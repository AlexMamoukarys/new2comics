package com.example.cms.controller;

import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.PreferredPublisher;
import com.example.cms.model.entity.PreferredPublisher;
import com.example.cms.model.repository.PreferredPublisherRepository;
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
public class PreferredPublisherController {
    @Autowired
    private final PreferredPublisherRepository repository;

    public PreferredPublisherController(PreferredPublisherRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/preferredpublishers")
    List<PreferredPublisher> retrieveAllPublishers() {
        return repository.findAll();
    }

    @PostMapping("/preferredpublishers")
    PreferredPublisher createPreferredPublisher(@RequestBody PreferredPublisher newPublisher) {
        return repository.save(newPublisher);
    }

    @DeleteMapping("/preferredpublishers/{id}")
    void deletePublisher(@PathVariable("id") Long publisherId) {
        repository.deleteById(publisherId);
    }

    @PutMapping("/preferredpublishers/{id}")
    PreferredPublisher updatePreferredPublisher(@PathVariable("id") long id, @RequestBody PreferredPublisher updatedPublisher) {
        
        PreferredPublisher relation = repository.findById(id).orElseThrow(() -> new RuntimeException("Relationship not found with id " + id));

        relation.setUser(updatedPublisher.getUser());
        relation.setPublisher(updatedPublisher.getPublisher());
        return repository.save(relation);
        
    }
}
