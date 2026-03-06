package com.example.cms.controller;

import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.CharacterRepository;
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
public class CharacterController {
    @Autowired
    private final CharacterRepository repository;

    public CharacterController(CharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/characters")
    List<Character> retrieveAllCharacters() {
        return repository.findAll();
    }

    @GetMapping("/characters/search/{searchstring}")
    List<Character> searchCharacters(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }
    
}
