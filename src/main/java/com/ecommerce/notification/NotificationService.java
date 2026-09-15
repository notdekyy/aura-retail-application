package com.ecommerce.notification;

import com.ecommerce.common.EventBus;

public class NotificationService {
    public NotificationService() {
        registerListeners();
    }

    private void registerListeners() {
        EventBus bus = EventBus.getInstance();
        bus.subscribe("USER_REGISTERED", event ->
                sendEmail(event.payload(), "Welcome to AURA! Your profile has been initialized."));
        bus.subscribe("ORDER_CONFIRMED", event ->
                sendEmail(event.payload(), "Order Confirmed! Your items have been allocated and paid for."));
        bus.subscribe("ORDER_REJECTED", event ->
                sendEmail(event.payload(), "Order Cancelled. Your payment was declined and stock was released."));
    }

    private void sendEmail(String targetRef, String message) {
        System.out.printf("[NotificationService - Email Outbox] Target: %s | Message: %s%n", targetRef, message);
    }
}