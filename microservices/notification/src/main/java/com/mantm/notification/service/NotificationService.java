package com.mantm.notification.service;

import com.mantm.clients.notification.NotificationRequest;
import com.mantm.notification.entity.Notification;
import com.mantm.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        Notification notification =
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("ManTran")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build();
        notificationRepository.save(notification);
    }

}
