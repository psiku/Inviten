package com.inviten.api.exception;


public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Meeting not found");
    }
}

