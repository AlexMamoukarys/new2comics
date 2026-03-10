package com.example.cms.controller;

import com.example.cms.controller.dto.CharacterVolumeDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.Character;
import com.example.cms.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CharacterVolumeController {
    @Autowired
    private final CharacterVolumeRepository repository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private VolumeRepository volumeRepository;

    public CharacterVolumeController(CharacterVolumeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/character_volume")
    List<CharacterVolume> retrieveAllCharacterVolume() {
        return repository.findAll();
    }

    @PostMapping("/character_volume")
    CharacterVolume createCharacterVolume(@RequestBody CharacterVolumeDto dto) {
        Character character = characterRepository.findById(dto.getCharacterId()).orElseThrow(
                () -> new CharacterNotFoundException(dto.getCharacterId()));

        Volume volume = volumeRepository.findById(dto.getVolumeId()).orElseThrow(
                () -> new VolumeNotFoundException(dto.getVolumeId()));

        CharacterVolumeKey key_ = new CharacterVolumeKey(dto.getCharacterId(), dto.getVolumeId());
        CharacterVolume new_ = new CharacterVolume();

        new_.setCharacterVolumeId(key_);
        new_.setCharacter(character);
        new_.setVolume(volume);

        return repository.save(new_);
    }

    @DeleteMapping("/character_volume/{characterId}/{volumeId}")
    void deleteCharacterVolume(@PathVariable("characterId") long characterId, @PathVariable("volumeId") Long volumeId) {
        CharacterVolumeKey characterVolumeKey = new CharacterVolumeKey(characterId, volumeId);
        repository.deleteById(characterVolumeKey);
    }

}