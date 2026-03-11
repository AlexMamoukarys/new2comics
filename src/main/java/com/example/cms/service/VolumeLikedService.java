package com.example.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.LikedVolumeRepository;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.VolumeRepository;

@Service
public class VolumeLikedService {

    private final LikedVolumeRepository likedVolumeRepository;
    private final UserRepository userRepository;
    private final VolumeRepository volumeRepository;

    public VolumeLikedService(LikedVolumeRepository likedVolumeRepository, UserRepository userRepository, VolumeRepository volumeRepository) {
        this.likedVolumeRepository = likedVolumeRepository;
        this.userRepository = userRepository;
        this.volumeRepository = volumeRepository;
    }

    @Transactional
    public void likeVolume(long userId, long volumeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Volume not found"));

        if (likedVolumeRepository.existsByUserAndVolume(user, volume)) {
            return; // User has already liked this volume, do nothing
        }

        // Create new relationship between user and volume
        LikedVolume likedVolume = new LikedVolume(user, volume);
        likedVolumeRepository.save(likedVolume);

        // Increase likes
        volumeRepository.incrementLikes(volumeId);

    }

    @Transactional
    public void unlikeVolume(long userId, long volumeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Volume not found"));

        LikedVolume likedVolume = likedVolumeRepository.findByUserAndVolume(user, volume).orElseThrow(() -> new RuntimeException("Like not found"));
        
        //Remove the like relationship
        likedVolumeRepository.delete(likedVolume);

        // Decrease likes
        volumeRepository.decrementLikes(volumeId);
    }

    @Transactional
    public void toggleLikeVolume(long userId, long volumeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Volume not found"));

        if (likedVolumeRepository.existsByUserAndVolume(user, volume)) {
            unlikeVolume(userId, volumeId);
        } else {
            likeVolume(userId, volumeId);
        }
    }

    public List<LikedVolume> getLikedVolumesForUser(Long userId) {
        return likedVolumeRepository.findAllByUserId(userId);
    }



}
