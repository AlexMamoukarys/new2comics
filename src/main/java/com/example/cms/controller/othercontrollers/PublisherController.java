package com.example.cms.controller;

import com.example.cms.model.entity.Publisher;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.PublisherRepository;
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
public class PublisherController {
    @Autowired
    private final PublisherRepository repository;

    public PublisherController(PublisherRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/publishers")
    List<Publisher> retrieveAllPublishers() {
        return repository.findAll();
    }
    
    @GetMapping("/publishers/search/{searchstring}")
    List<Publisher> searchPublishers(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }
}