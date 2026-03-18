package com.example.cms.controller;

import com.example.cms.model.entity.Character;
import com.example.cms.model.repository.CharacterRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(Character.PATH)
public class CharacterController extends PropertyController<CharacterRepository, Character> {

    public CharacterController(CharacterRepository repository) {
        super(repository);
    }
}
