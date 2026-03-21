package com.example.cms.controller;

import com.example.cms.model.entity.Character;
import com.example.cms.model.entity.PreferredCharacter;
import com.example.cms.model.repository.CharacterRepository;
import com.example.cms.model.repository.PreferredCharacterRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(PreferredCharacter.PATH)
public class PreferredCharacterController extends PreferredController<PreferredCharacterRepository, CharacterRepository, PreferredCharacter, Character> {

    public PreferredCharacterController(PreferredCharacterRepository repository) {
        super(repository, PreferredCharacter.TABLE, PreferredCharacter.NAME_ID);
    }
}
