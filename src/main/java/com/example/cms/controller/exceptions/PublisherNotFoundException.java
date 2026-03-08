package com.example.cms.controller.exceptions;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(Long publisherId) {
        super("Could not find publisher " + publisherId);
    }
}
