package com.sarinhem.notification.controller;

import com.sarinhem.notification.dto.NotificationRequest;
import com.sarinhem.notification.model.Notification;
import com.sarinhem.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@Valid @RequestBody NotificationRequest request) {
        Notification notification = service.send(request);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        Notification n = service.getById(id);
        if (n == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(n);
    }
}