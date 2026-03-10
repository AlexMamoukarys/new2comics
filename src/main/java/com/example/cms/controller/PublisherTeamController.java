package com.example.cms.controller;

import com.example.cms.controller.dto.PublisherTeamDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.Publisher;
import com.example.cms.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PublisherTeamController {

    @Autowired
    private final PublisherTeamRepository repository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private TeamRepository teamRepository;

    public PublisherTeamController(PublisherTeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/publisher_team")
    List<PublisherTeam> retrieveAllPublisherTeam() {
        return repository.findAll();
    }

    @PostMapping("/publisher_team")
    PublisherTeam createPublisherTeam(@RequestBody PublisherTeamDto dto) {

        Publisher publisher = publisherRepository.findById(dto.getPublisherId()).orElseThrow(
                () -> new PublisherNotFoundException(dto.getPublisherId()));

        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow(
                () -> new TeamNotFoundException(dto.getTeamId()));

        PublisherTeamKey key_ = new PublisherTeamKey(dto.getPublisherId(), dto.getTeamId());
        PublisherTeam new_ = new PublisherTeam();

        new_.setPublisherTeamId(key_);
        new_.setPublisher(publisher);
        new_.setTeam(team);

        return repository.save(new_);
    }

    @DeleteMapping("/publisher_team/{publisherId}/{teamId}")
    void deletePublisherTeam(@PathVariable("publisherId") long publisherId, @PathVariable("teamId") Long teamId) {
        PublisherTeamKey publisherTeamKey = new PublisherTeamKey(publisherId, teamId);
        repository.deleteById(publisherTeamKey);
    }

}