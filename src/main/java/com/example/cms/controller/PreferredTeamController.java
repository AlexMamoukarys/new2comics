package com.example.cms.controller;

import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.entity.PreferredTeam;
import com.example.cms.model.entity.PreferredTeam;
import com.example.cms.model.repository.PreferredTeamRepository;
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
public class PreferredTeamController {
    @Autowired
    private final PreferredTeamRepository repository;

    public PreferredTeamController(PreferredTeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/preferredteams")
    List<PreferredTeam> retrieveAllTeams() {
        return repository.findAll();
    }

    @PostMapping("/preferredteams")
    PreferredTeam createPreferredTeam(@RequestBody PreferredTeam newTeam) {
        return repository.save(newTeam);
    }

    @DeleteMapping("/preferredteams/{id}")
    void deleteTeam(@PathVariable("id") Long teamId) {
        repository.deleteById(teamId);
    }

    @PutMapping("/preferredteams/{id}")
    PreferredTeam updatePreferredTeam(@PathVariable("id") long id, @RequestBody PreferredTeam updatedTeam) {
        
        PreferredTeam relation = repository.findById(id).orElseThrow(() -> new RuntimeException("Relationship not found with id " + id));

        relation.setUser(updatedTeam.getUser());
        relation.setTeam(updatedTeam.getTeam());
        return repository.save(relation);
        
    }

    @DeleteMapping("/preferredteams/{user_id}/{teams_id}")
    void foreignDeletePrefTeam(@PathVariable("teams_id") Long teamsId, @PathVariable("user_id") Long userId) {
        repository.foreignDelete(teamsId, userId);
    }
}
