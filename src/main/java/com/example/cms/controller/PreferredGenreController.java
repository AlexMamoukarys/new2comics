package com.example.cms.controller;

import com.example.cms.model.entity.PreferredGenre;
import com.example.cms.model.repository.PreferredGenreRepository;
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
public class PreferredGenreController {
    @Autowired
    private final PreferredGenreRepository repository;

    public PreferredGenreController(PreferredGenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/preferredgenres")
    List<PreferredGenre> retrieveAllGenres() {
        return repository.findAll();
    }

    @PostMapping("/preferredgenres")
    PreferredGenre createPreferredGenre(@RequestBody PreferredGenre newGenre) {
        return repository.save(newGenre);
    }

    @DeleteMapping("/preferredgenres/{id}")
    void deleteGenre(@PathVariable("id") Long genreId) {
        repository.deleteById(genreId);
    }
}
