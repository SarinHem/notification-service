package com.sarinhem.notification.service.impl;

import com.sarinhem.notification.dto.NotificationRequest;
import com.sarinhem.notification.model.Notification;
import com.sarinhem.notification.model.NotificationStatus;
import com.sarinhem.notification.repository.NotificationRepository;
import com.sarinhem.notification.service.NotificationService;
import com.sarinhem.notification.model.NotificationType;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender = mailSender;
    }

    @Override
    @Transactional
    public Notification send(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipient(request.getRecipient());
        notification.setType(request.getType());
        notification.setSubject(request.getSubject());
        notification.setBody(request.getBody());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(OffsetDateTime.now());

        notification = repository.save(notification);

        // Only EMAIL implemented here; add SMS/PUSH integrations as needed
        if (request.getType() == NotificationType.EMAIL) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(request.getRecipient());
                message.setSubject(request.getSubject() == null ? "(no subject)" : request.getSubject());
                message.setText(request.getBody());
                mailSender.send(message);

                notification.setStatus(NotificationStatus.SENT);
                notification.setSentAt(OffsetDateTime.now());
                repository.save(notification);
            } catch (MailException ex) {
                notification.setStatus(NotificationStatus.FAILED);
                repository.save(notification);
                // Optionally rethrow or log
            }
        } else {
            // For SMS/PUSH: enqueue or call external provider, update status accordingly.
            // For now mark as SENT placeholder
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(OffsetDateTime.now());
            repository.save(notification);
        }

        return notification;
    }

    @Override
    public Notification getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}