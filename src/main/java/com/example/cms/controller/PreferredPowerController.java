package com.example.cms.controller;

import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.PreferredPower;
import com.example.cms.model.repository.PreferredPowerRepository;
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
public class PreferredPowerController {
    @Autowired
    private final PreferredPowerRepository repository;

    public PreferredPowerController(PreferredPowerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/preferredpowers")
    List<PreferredPower> retrieveAllPowers() {
        return repository.findAll();
    }

    @PostMapping("/preferredpowers")
    PreferredPower createPreferredPower(@RequestBody PreferredPower newPower) {
        return repository.save(newPower);
    }

    @DeleteMapping("/preferredpowers/{id}")
    void deletePower(@PathVariable("id") Long powerId) {
        repository.deleteById(powerId);
    }

    @PutMapping("/preferredpowers/{id}")
    PreferredPower updatePreferredPower(@PathVariable("id") long id, @RequestBody PreferredPower updatedPower) {
        
        PreferredPower relation = repository.findById(id).orElseThrow(() -> new RuntimeException("Relationship not found with id " + id));

        relation.setUser(updatedPower.getUser());
        relation.setPower(updatedPower.getPower());
        return repository.save(relation);
        
    }
}
