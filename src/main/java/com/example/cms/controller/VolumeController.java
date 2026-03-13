package com.example.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.service.VolumeLikedService;
import com.example.cms.service.VolumeSavedService;

@CrossOrigin
@RestController
public class VolumeController {
    @Autowired
    private final VolumeRepository repository;
    private final VolumeLikedService volumeLikedService;
    private final VolumeSavedService volumeSavedService;

    public VolumeController(VolumeRepository repository, VolumeLikedService volumeLikedService, VolumeSavedService volumeSavedService) {
        this.repository = repository;
        this.volumeLikedService = volumeLikedService;
        this.volumeSavedService = volumeSavedService;
    }

    @GetMapping("/volumes")
    List<Volume> retrieveAllVolumes() {
        return repository.findAll();
    }

    @GetMapping("/volumes/popular")
    List<Volume> retrievePopularVolumes() { return repository.retrievePopularVolumes(); }

    @PostMapping("/volumes")
    Volume createVolume(@RequestBody Volume newVolume) {
        return repository.save(newVolume);
    }

    @DeleteMapping("/volumes/{id}")
    void deleteVolume(@PathVariable("id") Long volumeId) {
        repository.deleteById(volumeId);
    }
    
    @GetMapping("/volumes/search/{user_id}/{searchstring}")
    List<Volume> searchVolume(@PathVariable("searchstring") String searchString, @PathVariable("user_id") long user_id) {
        return repository.search(searchString, user_id);
    }

    //@PostMapping("/volumes/incrementlikes/{id}")
    //void incrementLikes(@PathVariable("id") long id) {
    //    repository.incrementLikes(id);
    //}


    // Like endpoints

    @PostMapping("/volumes/{volume_id}/togglelike/{user_id}")
    void toggleLikeVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        volumeLikedService.toggleLikeVolume(userId, volumeId);
    }

    @DeleteMapping("/volumes/{volume_id}/like/{user_id}")
    void unlikeVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        volumeLikedService.unlikeVolume(userId, volumeId);
    }

    @PostMapping("/volumes/{volume_id}/like/{user_id}")
    void likeVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        volumeLikedService.likeVolume(userId, volumeId);
    }

    @GetMapping("/volumes/liked/{user_id}")
    List<LikedVolume> getLikedVolumesForUser(@PathVariable("user_id") long userId) {
        return volumeLikedService.getLikedVolumesForUser(userId);
    }

    // Save endpoints

    @PostMapping("/volumes/{volume_id}/togglesave/{user_id}")
    void toggleSaveVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        volumeSavedService.toggleSaveVolume(userId, volumeId);
    }

    @DeleteMapping("/volumes/{volume_id}/save/{user_id}")
    void unsaveVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        volumeSavedService.unsaveVolume(userId, volumeId);
    }

    @PostMapping("/volumes/{volume_id}/save/{user_id}")
    void saveVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        volumeSavedService.saveVolume(userId, volumeId);
    }

    @GetMapping("/volumes/saved/{user_id}")
    List<SavedVolume> getSavedVolumesForUser(@PathVariable("user_id") long userId) {
        return volumeSavedService.getSavedVolumesForUser(userId);
    }

    // Updating Volume info
    @PutMapping("/volumes/{id}")
    Volume updateVolume(@PathVariable("id") long id, @RequestBody Volume updatedVolume) {
        
        Volume volume = repository.findById(id).orElseThrow(() -> new RuntimeException("Volume not found with id " + id));

        volume.setName(updatedVolume.getName());
        volume.setNumIssues(updatedVolume.getNumIssues());
        volume.setDeck(updatedVolume.getDeck());
        volume.setStartYear(updatedVolume.getStartYear());
        volume.setImage(updatedVolume.getImage());
        volume.setNumLikes(updatedVolume.getNumLikes());

        return repository.save(volume);
        
    }
    

}
