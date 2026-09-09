package com.ecommerce.common;

import java.time.Instant;
import java.util.UUID;

public record DomainEvent(String eventType, String payload, Instant timestamp, String correlationId) {
    public DomainEvent(String eventType, String payload) {
        this(eventType, payload, Instant.now(), UUID.randomUUID().toString());
    }
}