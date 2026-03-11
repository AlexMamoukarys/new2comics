package com.example.cms.controller;

import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.service.VolumeLikedService;
import com.example.cms.service.VolumeSavedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class VolumeController {
    @Autowired
    private final VolumeRepository repository;
    private final VolumeLikedService VolumeLikedService;
    private final VolumeSavedService VolumeSavedService;

    public VolumeController(VolumeRepository repository, VolumeLikedService volumeLikedService, VolumeSavedService VolumeSavedService) {
        this.repository = repository;
        this.VolumeLikedService = volumeLikedService;
        this.VolumeSavedService = VolumeSavedService;
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

    @PostMapping("/volumes/{volume_id}/tooglelike/{user_id}")
    void toggleLikeVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        VolumeLikedService.toggleLikeVolume(userId, volumeId);
    }

    @DeleteMapping("/volumes/{volume_id}/like/{user_id}")
    void unlikeVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        VolumeLikedService.unlikeVolume(userId, volumeId);
    }

    @PostMapping("/volumes/{volume_id}/like/{user_id}")
    void likeVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        VolumeLikedService.likeVolume(userId, volumeId);
    }

    @GetMapping("/volumes/liked/{user_id}")
    List<LikedVolume> getLikedVolumesForUser(@PathVariable("user_id") long userId) {
        return VolumeLikedService.getLikedVolumesForUser(userId);
    }

    // Save endpoints

    @PostMapping("/volumes/{volume_id}/tooglesave/{user_id}")
    void toggleSaveVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        VolumeSavedService.toggleSaveVolume(userId, volumeId);
    }

    @DeleteMapping("/volumes/{volume_id}/save/{user_id}")
    void unsaveVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        VolumeSavedService.unsaveVolume(userId, volumeId);
    }

    @PostMapping("/volumes/{volume_id}/save/{user_id}")
    void saveVolume(@PathVariable("volume_id") long volumeId, @PathVariable("user_id") long userId) {
        VolumeSavedService.saveVolume(userId, volumeId);
    }

    @GetMapping("/volumes/saved/{user_id}")
    List<SavedVolume> getSavedVolumesForUser(@PathVariable("user_id") long userId) {
        return VolumeSavedService.getSavedVolumesForUser(userId);
    }

    

}
