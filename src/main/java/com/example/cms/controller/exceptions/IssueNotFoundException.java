package com.example.cms.controller.exceptions;

public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(Long id) {
        super("Could not find issue " + id);
    }
}
