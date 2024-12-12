package com.verve.service;

import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    public void logUniqueRequestCount(int count) {
        // Log the unique request count to an external system or file
        // Example: log to a file or external logging service
        System.out.println("Logging unique request count: " + count);
    }
}
