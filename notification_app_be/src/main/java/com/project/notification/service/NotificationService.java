package com.project.notification.service;

import com.project.notification.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotificationService {

    private final List<Notification> notifications = new ArrayList<>();

    @Autowired
    private LoggingService loggingService;

    public Notification createNotification(String type, String message, String recipient) {

        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                type,
                message,
                recipient
        );

        notifications.add(notification);

        // 🔥 Logging success
        loggingService.log("backend", "info", "service", "Notification created");

        return notification;
    }

    public List<Notification> getAllNotifications() {

        loggingService.log("backend", "info", "service", "Fetching notifications");

        return notifications;
    }
}