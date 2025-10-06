// 代码生成时间: 2025-10-06 22:45:45
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NotificationService {

    private static final String NOTIFICATION_TEMPLATE = "Notification: %s";

    public String sendNotification(String message) {
        // Check if the message is not null or empty
        if (message == null || message.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be null or empty");
        }

        // Create and return the notification message
        return String.format(NOTIFICATION_TEMPLATE, message);
    }

    // Error handling method to simulate error conditions
    public void handleError() {
        // Simulate an error by throwing an exception
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the notification");
    }
}
