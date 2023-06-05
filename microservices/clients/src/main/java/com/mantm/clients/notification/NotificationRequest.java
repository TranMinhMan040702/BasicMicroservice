package com.mantm.clients.notification;

public record NotificationRequest(Integer toCustomerId, String toCustomerEmail, String message) {
}
