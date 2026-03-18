package com.example.cms.controller;

import com.example.cms.controller.exceptions.UserNotFoundException;
import com.example.cms.model.entity.Genre;
import com.example.cms.model.entity.Preferred;
import com.example.cms.model.entity.Property;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.GenreRepository;
import com.example.cms.model.repository.PreferredRepository;
import com.example.cms.model.repository.PropertyRepository;
import com.example.cms.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public abstract class PreferredController<T extends PreferredRepository<P>, R extends PropertyRepository<I>, P extends Preferred<I>, I extends Property> {
    private final T repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private R iRepository;

    protected PreferredController(T repository) {
        this.repository = repository;
    }

    @GetMapping()
    public List<P> retrieveAll() {
        return repository.findAll();
    }

    @PostMapping()
    public P createPreferred(@RequestBody P newP) {
        User user = userRepository.findById(newP.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(0L));

        I i = iRepository.findById(newP.getPreference().getId())
                .orElseThrow(() -> new RuntimeException("I not found"));


        newP.setUser(user);
        newP.setPreference(i);

        return repository.save(newP);
    }

    @DeleteMapping("/{id}")
    public void deletePreferred(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public P updatePreferred(@PathVariable("id") long id, @RequestBody P updatedP) {
        P relation = repository.findById(id).orElseThrow(() -> new RuntimeException("Relationship not found with id " + id));
        relation.setUser(updatedP.getUser());
        relation.setPreference(updatedP.getPreference());
        return repository.save(relation);
    }
}
