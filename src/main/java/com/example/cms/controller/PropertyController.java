package com.example.cms.controller;

import com.example.cms.model.entity.Property;
import com.example.cms.model.repository.PropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public abstract class PropertyController<T extends PropertyRepository<P>, P extends Property> {
    private final T repository;

    protected PropertyController(T repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<P> retrieveAll() {
        return repository.findAll();
    }

    @GetMapping("/search/{searchstring}")
    public List<P> search(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }
}
