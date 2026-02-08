package com.example.cms.controller.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String code) {
        super("Could not find course " + code);
    }
}
