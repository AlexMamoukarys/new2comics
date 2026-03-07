package com.example.cms.controller;

import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.Volume;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.TeamRepository;
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
public class TeamController {
    @Autowired
    private final TeamRepository repository;

    public TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/teams")
    List<Team> retrieveAllTeams() {
        return repository.findAll();
    }

    @GetMapping("/teams/search/{searchstring}")
    List<Team> searchTeam(@PathVariable("searchstring") String searchString) {
        return repository.search(searchString);
    }
}