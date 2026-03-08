package com.example.cms.controller.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Long teamId) {
        super("Could not find team" + teamId);
    }
}
