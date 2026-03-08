package com.example.cms.controller;

import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.GenreRepository;
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
public class GenreController {
    @Autowired
    private final GenreRepository repository;

    public GenreController(GenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/genres")
    List<Genre> retrieveAllGenres() {
        return repository.findAll();
    }

    @GetMapping("/genres/search/{searchstring}")
    List<Genre> searchGenres(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }
    
}