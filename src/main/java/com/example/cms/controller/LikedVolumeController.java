package com.example.cms.controller;

import com.example.cms.model.entity.LikedVolume;
import com.example.cms.model.repository.LikedVolumeRepository;
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
public class LikedVolumeController {
    @Autowired
    private final LikedVolumeRepository repository;

    public LikedVolumeController(LikedVolumeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/likedvolumes")
    List<LikedVolume> retrieveAllVolumes() {
        return repository.findAll();
    }

    @PostMapping("/likedvolumes")
    LikedVolume createLikedVolume(@RequestBody LikedVolume newVolume) {
        return repository.save(newVolume);
    }

    @DeleteMapping("/likedvolumes/{id}")
    void deleteVolume(@PathVariable("id") Long volumeId) {
        repository.deleteById(volumeId);
    }
}
