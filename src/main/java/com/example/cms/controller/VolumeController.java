package com.example.cms.controller;

import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.VolumeRepository;
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

    public VolumeController(VolumeRepository repository) {
        this.repository = repository;
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
    
    @GetMapping("/volumes/search/{searchstring}")
    List<Volume> searchVolume(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }

    @PostMapping("/volumes/incrementlikes/{id}")
    void incrementLikes(@PathVariable("id") long id) {
        repository.incrementLikes(id);
    }
}
