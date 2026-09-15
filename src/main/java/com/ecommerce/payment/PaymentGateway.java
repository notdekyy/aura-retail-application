package com.ecommerce.payment;

import java.math.BigDecimal;

public interface PaymentGateway {
    boolean processPayment(String orderId, BigDecimal amount);
}