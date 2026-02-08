package com.example.cms.controller.exceptions;

public class ClassroomNotFoundException extends RuntimeException {
    public ClassroomNotFoundException(String code) {
        super("Could not find classroom " + code);
    }
}
