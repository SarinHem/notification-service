package com.sarinhem.notification.dto;

import com.sarinhem.notification.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {
    @NotBlank
    private String recipient;

    @NotNull
    private NotificationType type;

    // Optional
    private String subject;

    @NotBlank
    private String body;

    public NotificationRequest() {}

    public NotificationRequest(String recipient, NotificationType type, String subject, String body) {
        this.recipient = recipient;
        this.type = type;
        this.subject = subject;
        this.body = body;
    }

    // getters/setters

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}