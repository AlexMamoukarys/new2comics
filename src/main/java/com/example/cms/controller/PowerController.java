package com.example.cms.controller;

import com.example.cms.model.entity.Power;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.PowerRepository;
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
public class PowerController {
    @Autowired
    private final PowerRepository repository;

    public PowerController(PowerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/powers")
    List<Power> retrieveAllPowers() {
        return repository.findAll();
    }
    
    @GetMapping("/powers/search/{searchstring}")
    List<Power> searchPowers(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }
}
