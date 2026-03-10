package com.example.cms.controller;

import com.example.cms.controller.dto.CharacterTeamDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.Character;
import com.example.cms.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CharacterTeamController {
    @Autowired
    private final CharacterTeamRepository repository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private TeamRepository teamRepository;

    public CharacterTeamController(CharacterTeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/character_team")
    List<CharacterTeam> retrieveAllCharacterTeam() {
        return repository.findAll();
    }

    @PostMapping("/character_team")
    CharacterTeam createCharacterTeam(@RequestBody CharacterTeamDto dto) {
        Character character = characterRepository.findById(dto.getCharacterId()).orElseThrow(
                () -> new CharacterNotFoundException(dto.getCharacterId()));

        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow(
                () -> new TeamNotFoundException(dto.getTeamId()));

        CharacterTeamKey key_ = new CharacterTeamKey(dto.getCharacterId(), dto.getTeamId());
        CharacterTeam new_ = new CharacterTeam();

        new_.setCharacterTeamId(key_);
        new_.setCharacter(character);
        new_.setTeam(team);

        return repository.save(new_);
    }

    @DeleteMapping("/character_team/{characterId}/{teamId}")
    void deleteCharacterTeam(@PathVariable("characterId") long characterId, @PathVariable("teamId") Long teamId) {
        CharacterTeamKey characterTeamKey = new CharacterTeamKey(characterId, teamId);
        repository.deleteById(characterTeamKey);
    }

}