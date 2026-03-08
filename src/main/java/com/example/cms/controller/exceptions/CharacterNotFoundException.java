package com.example.cms.controller.exceptions;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(Long characterId) {
        super("Could not find character " + characterId);
    }
}
