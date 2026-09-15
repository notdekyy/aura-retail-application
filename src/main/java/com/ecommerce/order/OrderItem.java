package com.ecommerce.order;

import java.math.BigDecimal;

public record OrderItem(String productId, int quantity, BigDecimal unitPrice) {
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}