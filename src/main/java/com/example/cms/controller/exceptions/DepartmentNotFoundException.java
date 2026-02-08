package com.example.cms.controller.exceptions;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(String code) {
        super("Could not find department " + code);
    }
}
