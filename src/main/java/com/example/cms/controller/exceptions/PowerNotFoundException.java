package com.example.cms.controller.exceptions;

public class PowerNotFoundException extends RuntimeException {
    public PowerNotFoundException(Long powerId) {
        super("Could not find power " + powerId);
    }
}
