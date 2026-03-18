package com.example.cms.controller;

import com.example.cms.controller.exceptions.UserNotFoundException;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.PreferredGenre;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.PreferredGenreRepository;
import com.example.cms.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
public class PreferredGenreController {
    @Autowired
    private final PreferredGenreRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    public PreferredGenreController(PreferredGenreRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/preferredgenres")
    List<PreferredGenre> retrieveAllGenres() {
        return repository.findAll();
    }

    @PostMapping("/preferredgenres")
    PreferredGenre createPreferredGenre(@RequestBody PreferredGenre newGenre) {
        User user = userRepository.findById(newGenre.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(0L));

        Genre genre = genreRepository.findById(newGenre.getGenre().getId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        if (repository.existsByUserAndGenre(user, genre)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "This genre is already in your preferences"
            );
        }

        newGenre.setUser(user);
        newGenre.setGenre(genre);

        return repository.save(newGenre);
    }

    @DeleteMapping("/preferredgenres/{id}")
    void deleteGenre(@PathVariable("id") Long genreId) {
        repository.deleteById(genreId);
    }

    @PutMapping("/preferredgenres/{id}")
    PreferredGenre updatePreferredGenre(@PathVariable("id") long id, @RequestBody PreferredGenre updatedGenre) {
        
        PreferredGenre relation = repository.findById(id).orElseThrow(() -> new RuntimeException("Relationship not found with id " + id));

        relation.setUser(updatedGenre.getUser());
        relation.setGenre(updatedGenre.getGenre());
        return repository.save(relation);
        
    }
}
