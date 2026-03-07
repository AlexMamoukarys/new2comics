package com.example.cms.controller;

import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.repository.PreferredCharacterRepository;
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
public class PreferredCharacterController {
    @Autowired
    private final PreferredCharacterRepository repository;

    public PreferredCharacterController(PreferredCharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/preferredcharacters")
    List<PreferredCharacter> retrieveAllCharacters() {
        return repository.findAll();
    }

    @PostMapping("/preferredcharacters")
    PreferredCharacter createPreferredCharacter(@RequestBody PreferredCharacter newCharacter) {
        return repository.save(newCharacter);
    }

    @DeleteMapping("/preferredcharacters/{id}")
    void deleteCharacter(@PathVariable("id") Long characterId) {
        repository.deleteById(characterId);
    }
}
