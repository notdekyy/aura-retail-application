package com.ecommerce.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class Order {
    private final String orderId;
    private final String userId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private OrderStatus status;
    private final Instant createdAt;

    public Order(String orderId, String userId, List<OrderItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = List.copyOf(items);
        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}