package com.example.cms.controller;

import com.example.cms.controller.dto.VolumeTeamDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.repository.VolumeTeamRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VolumeTeamController {
    @Autowired
    private final VolumeTeamRepository repository;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private TeamRepository teamRepository;

    public VolumeTeamController(VolumeTeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/volume_team")
    List<VolumeTeam> retrieveAllVolumeTeam() {
        return repository.findAll();
    }

    @PostMapping("/volume_team")
    VolumeTeam createMark(@RequestBody VolumeTeamDto volumeTeamDto) {

        Volume volume = volumeRepository.findById(volumeTeamDto.getVolumeId()).orElseThrow(
                () -> new VolumeNotFoundException(volumeTeamDto.getVolumeId()));

        Team team = teamRepository.findById(volumeTeamDto.getTeamId()).orElseThrow(
                () -> new TeamNotFoundException(volumeTeamDto.getTeamId()));

        VolumeTeamKey volumeTeamKey = new VolumeTeamKey(volumeTeamDto.getVolumeId(), volumeTeamDto.getTeamId());
        VolumeTeam newVolumeTeam = new VolumeTeam();

        newVolumeTeam.setVolumeTeamId(volumeTeamKey);

        newVolumeTeam.setVolume(volume);
        newVolumeTeam.setTeam(team);

        return repository.save(newVolumeTeam);
    }

    @DeleteMapping("/volume_team/{volumeId}/{teamId}")
    void deleteCourseMark(@PathVariable("volumeId") long volumeId, @PathVariable("teamId") Long teamId) {
        VolumeTeamKey volumeTeamKey = new VolumeTeamKey(volumeId, teamId);
        repository.deleteById(volumeTeamKey);
    }

}
