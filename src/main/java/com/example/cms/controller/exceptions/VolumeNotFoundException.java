package com.example.cms.controller.exceptions;

public class VolumeNotFoundException extends RuntimeException {
    public VolumeNotFoundException(Long volumeId) {
        super("Could not find volume " + volumeId);
    }
}

