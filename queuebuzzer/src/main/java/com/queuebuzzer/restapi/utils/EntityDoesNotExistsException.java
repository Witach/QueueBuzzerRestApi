package com.queuebuzzer.restapi.utils;


public class EntityDoesNotExistsException extends RuntimeException {
    public EntityDoesNotExistsException(String message) {
        super(message);
    }
}
