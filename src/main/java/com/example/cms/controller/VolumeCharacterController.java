package com.example.cms.controller;

import com.example.cms.controller.dto.VolumeCharacterDto;
import com.example.cms.controller.exceptions.*;
import com.example.cms.model.entity.*;
import com.example.cms.model.entity.Character;
import com.example.cms.model.repository.VolumeCharacterRepository;
import com.example.cms.model.repository.VolumeRepository;
import com.example.cms.model.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class VolumeCharacterController {
    @Autowired
    private final VolumeCharacterRepository repository;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private CharacterRepository characterRepository;

    public VolumeCharacterController(VolumeCharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/volume_character")
    List<VolumeCharacter> retrieveAllVolumeCharacter() {
        return repository.findAll();
    }

    @PostMapping("/volume_character")
    VolumeCharacter createMark(@RequestBody VolumeCharacterDto volumeCharacterDto) {

        Volume volume = volumeRepository.findById(volumeCharacterDto.getVolumeId()).orElseThrow(
                () -> new VolumeNotFoundException(volumeCharacterDto.getVolumeId()));

        Character character = characterRepository.findById(volumeCharacterDto.getCharacterId()).orElseThrow(
                () -> new CharacterNotFoundException(volumeCharacterDto.getCharacterId()));

        VolumeCharacterKey volumeCharacterKey = new VolumeCharacterKey(volumeCharacterDto.getVolumeId(), volumeCharacterDto.getCharacterId());
        VolumeCharacter newVolumeCharacter = new VolumeCharacter();

        newVolumeCharacter.setVolumeCharacterId(volumeCharacterKey);

        newVolumeCharacter.setVolume(volume);
        newVolumeCharacter.setCharacter(character);

        return repository.save(newVolumeCharacter);
    }

    @DeleteMapping("/volume_character/{volumeId}/{characterId}")
    void deleteCourseMark(@PathVariable("volumeId") long volumeId, @PathVariable("characterId") Long characterId) {
        VolumeCharacterKey volumeCharacterKey = new VolumeCharacterKey(volumeId, characterId);
        repository.deleteById(volumeCharacterKey);
    }

}
