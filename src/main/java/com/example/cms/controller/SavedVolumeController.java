package com.example.cms.controller;

import com.example.cms.model.entity.SavedVolume;
import com.example.cms.model.repository.SavedVolumeRepository;
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
public class SavedVolumeController {
    @Autowired
    private final SavedVolumeRepository repository;

    public SavedVolumeController(SavedVolumeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/savedvolumes")
    List<SavedVolume> retrieveAllVolumes() {
        return repository.findAll();
    }

    @PostMapping("/savedvolumes")
    SavedVolume createLikedVolume(@RequestBody SavedVolume newVolume) {
        return repository.save(newVolume);
    }

    @DeleteMapping("/savedvolumes/{id}")
    void deleteVolume(@PathVariable("id") Long volumeId) {
        repository.deleteById(volumeId);
    }
}
