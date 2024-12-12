package com.verve.exception;

public class CustomApplicationException extends RuntimeException {
    public CustomApplicationException(String message) {
        super(message);
    }
}
