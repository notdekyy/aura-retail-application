package com.ecommerce.order;

import com.ecommerce.catalog.CatalogService;
import com.ecommerce.catalog.Product;
import com.ecommerce.common.DomainEvent;
import com.ecommerce.common.EventBus;
import com.ecommerce.payment.PaymentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OrderService {
    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final CatalogService catalogService;
    private final PaymentService paymentService;

    public OrderService(CatalogService catalogService, PaymentService paymentService) {
        this.catalogService = catalogService;
        this.paymentService = paymentService;
    }

    public synchronized Order checkout(String userId, Map<String, Integer> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty for checkout");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            Product p = catalogService.getProduct(entry.getKey());
            if (p.getStock() < entry.getValue()) {
                throw new IllegalStateException("Insufficient inventory for product: " + p.getName());
            }
            orderItems.add(new OrderItem(p.getId(), entry.getValue(), p.getPrice()));
        }

        for (OrderItem item : orderItems) {
            Product p = catalogService.getProduct(item.productId());
            p.deductStock(item.quantity());
        }

        Order order = new Order(UUID.randomUUID().toString(), userId, orderItems);
        orders.put(order.getOrderId(), order);
        EventBus.getInstance().publish(new DomainEvent("ORDER_CREATED", order.getOrderId()));

        boolean paymentApproved = paymentService.charge(order.getOrderId(), order.getTotalAmount());

        if (paymentApproved) {
            order.setStatus(OrderStatus.CONFIRMED);
            EventBus.getInstance().publish(new DomainEvent("ORDER_CONFIRMED", order.getOrderId()));
        } else {
            for (OrderItem item : orderItems) {
                Product p = catalogService.getProduct(item.productId());
                p.addStock(item.quantity());
            }
            order.setStatus(OrderStatus.REJECTED);
            EventBus.getInstance().publish(new DomainEvent("ORDER_REJECTED", order.getOrderId()));
        }

        return order;
    }

    public Optional<Order> getOrder(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}