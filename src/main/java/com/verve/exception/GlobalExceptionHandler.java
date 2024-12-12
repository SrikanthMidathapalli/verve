package com.verve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    // Handle General Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        logger.severe("Unexpected Error: " + ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", "Unexpected error occurred");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        logger.warning("Request Error: " + ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getReason());
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    // Handle Custom Application Exceptions (if any)
    @ExceptionHandler(CustomApplicationException.class)
    public ResponseEntity<Map<String, String>> handleCustomException(CustomApplicationException ex) {
        logger.warning("Application Error: " + ex.getMessage());
        Map<String, String> response = new HashMap<>();
        response.put("error", "Application-specific error");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
