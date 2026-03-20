package com.example.cms.controller;

import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.Volume;
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

    @PutMapping("/preferredcharacters/{id}")
    PreferredCharacter updatePreferredCharacter(@PathVariable("id") long id, @RequestBody PreferredCharacter updatedCharacter) {
        
        PreferredCharacter relation = repository.findById(id).orElseThrow(() -> new RuntimeException("Relationship not found with id " + id));

        relation.setUser(updatedCharacter.getUser());
        relation.setCharacter(updatedCharacter.getCharacter());
        return repository.save(relation);
        
    }

    @DeleteMapping("/preferredcharacters/{user_id}/{char_id}")
    void foreignDeletePrefChar(@PathVariable("char_id") Long charId, @PathVariable("user_id") Long userId) {
        repository.foreignDelete(charId, userId);
    }
}
