package com.ecommerce.payment;

import com.ecommerce.common.DomainEvent;
import com.ecommerce.common.EventBus;

import java.math.BigDecimal;

public class PaymentService {
    private final PaymentGateway gateway;

    public PaymentService(PaymentGateway gateway) {
        this.gateway = gateway;
        registerEventListener();
    }

    private void registerEventListener() {
        EventBus.getInstance().subscribe("ORDER_CREATED", event -> {
            System.out.println("[PaymentService - Async Daemon] Hook received for Order: " + event.payload());
        });
    }

    public boolean charge(String orderId, BigDecimal amount) {
        boolean success = gateway.processPayment(orderId, amount);
        if (success) {
            EventBus.getInstance().publish(new DomainEvent("PAYMENT_SUCCESSFUL", orderId));
        } else {
            EventBus.getInstance().publish(new DomainEvent("PAYMENT_FAILED", orderId));
        }
        return success;
    }
}