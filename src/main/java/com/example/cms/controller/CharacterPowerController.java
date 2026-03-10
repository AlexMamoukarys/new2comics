package com.example.cms.controller;

import com.example.cms.controller.dto.CharacterPowerDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.Character;
import com.example.cms.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CharacterPowerController {
    @Autowired
    private final CharacterPowerRepository repository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private PowerRepository powerRepository;

    public CharacterPowerController(CharacterPowerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/character_power")
    List<CharacterPower> retrieveAllCharacterPower() {
        return repository.findAll();
    }

    @PostMapping("/character_power")
    CharacterPower createCharacterPower(@RequestBody CharacterPowerDto dto) {
        Character character = characterRepository.findById(dto.getCharacterId()).orElseThrow(
                () -> new CharacterNotFoundException(dto.getCharacterId()));

        Power power = powerRepository.findById(dto.getPowerId()).orElseThrow(
                () -> new PowerNotFoundException(dto.getPowerId()));

        CharacterPowerKey key_ = new CharacterPowerKey(dto.getCharacterId(), dto.getPowerId());
        CharacterPower new_ = new CharacterPower();

        new_.setCharacterPowerId(key_);
        new_.setCharacter(character);
        new_.setPower(power);

        return repository.save(new_);
    }

    @DeleteMapping("/character_power/{characterId}/{powerId}")
    void deleteCharacterPower(@PathVariable("characterId") long characterId, @PathVariable("powerId") Long powerId) {
        CharacterPowerKey characterPowerKey = new CharacterPowerKey(characterId, powerId);
        repository.deleteById(characterPowerKey);
    }

}