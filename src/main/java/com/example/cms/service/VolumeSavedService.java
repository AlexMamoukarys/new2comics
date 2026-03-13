package com.example.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.entity.User;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.SavedVolumeRepository;
import com.example.cms.model.repository.UserRepository;
import com.example.cms.model.repository.VolumeRepository;

@Service
public class VolumeSavedService {

    private final SavedVolumeRepository savedVolumeRepository;
    private final UserRepository userRepository;
    private final VolumeRepository volumeRepository;

    public VolumeSavedService(SavedVolumeRepository savedVolumeRepository, UserRepository userRepository, VolumeRepository volumeRepository) {
        this.savedVolumeRepository = savedVolumeRepository;
        this.userRepository = userRepository;
        this.volumeRepository = volumeRepository;
    }

    @Transactional
    public void saveVolume(long userId, long volumeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Volume not found"));

        if (savedVolumeRepository.existsByUserAndVolume(user, volume)) {
            return; // User has already saved this volume, do nothing
        }

        // Create new relationship between user and volume
        SavedVolume savedVolume = new SavedVolume(user, volume);
        savedVolumeRepository.save(savedVolume);
    }

    @Transactional
    public void unsaveVolume(long userId, long volumeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Volume not found"));

        SavedVolume savedVolume = savedVolumeRepository.findByUserAndVolume(user, volume).orElseThrow(() -> new RuntimeException("Save not found"));
        
        //Remove the save relationship
        savedVolumeRepository.delete(savedVolume);

    }

    @Transactional
    public void toggleSaveVolume(long userId, long volumeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Volume volume = volumeRepository.findById(volumeId).orElseThrow(() -> new RuntimeException("Volume not found"));

        if (savedVolumeRepository.existsByUserAndVolume(user, volume)) {
            unsaveVolume(userId, volumeId);
        } else {
            saveVolume(userId, volumeId);
        }
    }

    public List<SavedVolume> getSavedVolumesForUser(Long userId) {
        return savedVolumeRepository.findAllByUserId(userId);
    }



}
