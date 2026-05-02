package com.project.notification.controller;

import com.project.notification.model.Notification;
import com.project.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ CREATE
    @PostMapping
    public Notification create(@RequestBody Notification request) {
        return notificationService.createNotification(request.getType(), request.getMessage(), request.getRecipient());
    }

    // ✅ GET ALL
    @GetMapping
    public List<Notification> getAll() {
        return notificationService.getAllNotifications();
    }
}