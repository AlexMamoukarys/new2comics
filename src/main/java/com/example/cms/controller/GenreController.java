package com.example.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.GenreRepository;

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

    @GetMapping("/genres/search/{searchstring}")            // Read
    List<Genre> searchGenres(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }

    @PostMapping("/genres")                                 // Create
    Genre createGenre(@RequestBody Genre newGenre) {
        return repository.save(newGenre);
    }

    @DeleteMapping("/genres/{id}")                          // Delete
    void deleteGenre(@PathVariable("id") Long genreId) {
        repository.deleteById(genreId);
    }

    @PostMapping("/genres/changeName/{id}/{name}")                 // Update
    void changeName(@PathVariable("id") long id, @PathVariable("name") String name) {
        repository.changeName(id, name);
    }


    
}