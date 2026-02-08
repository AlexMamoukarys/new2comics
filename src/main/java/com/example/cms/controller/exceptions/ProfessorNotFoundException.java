package com.example.cms.controller.exceptions;

public class ProfessorNotFoundException extends RuntimeException{
    public ProfessorNotFoundException(Long id) {
        super("Could not find professor " + id);
    }
}
