package com.example.cms.controller;

import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.VolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
