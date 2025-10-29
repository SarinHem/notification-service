package com.sarinhem.notification.service;

import com.sarinhem.notification.dto.NotificationRequest;
import com.sarinhem.notification.model.Notification;

public interface NotificationService {
    Notification send(NotificationRequest request);
    Notification getById(Long id);
}