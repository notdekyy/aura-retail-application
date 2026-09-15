package com.ecommerce;

import com.ecommerce.catalog.CatalogService;
import com.ecommerce.catalog.Product;
import com.ecommerce.catalog.ProductRepository;
import com.ecommerce.order.Order;
import com.ecommerce.order.OrderService;
import com.ecommerce.order.OrderStatus;
import com.ecommerce.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceTest {
    private CatalogService catalogService;
    private OrderService orderService;
    private Product testItem;

    @BeforeEach
    void setUp() {
        catalogService = new CatalogService(new ProductRepository());
        testItem = catalogService.createProduct("Gaming Console", new BigDecimal("499.99"), 5);

        PaymentService successfulPaymentService = new PaymentService((id, amount) -> true);
        orderService = new OrderService(catalogService, successfulPaymentService);
    }

    @Test
    @DisplayName("Should successfully allocate inventory and mark order confirmed")
    void testSuccessfulOrderCheckout() {
        Order order = orderService.checkout("user-101", Map.of(testItem.getId(), 2));

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(3, catalogService.getProduct(testItem.getId()).getStock());
        assertEquals(new BigDecimal("999.98"), order.getTotalAmount());
    }

    @Test
    @DisplayName("Should reject order and roll back stock if payment fails")
    void testCompensatingTransactionOnPaymentFailure() {
        PaymentService failingPaymentService = new PaymentService((id, amount) -> false);
        OrderService failingOrderService = new OrderService(catalogService, failingPaymentService);

        Order order = failingOrderService.checkout("user-101", Map.of(testItem.getId(), 2));

        assertEquals(OrderStatus.REJECTED, order.getStatus());
        assertEquals(5, catalogService.getProduct(testItem.getId()).getStock(), "Stock must be fully restored");
    }

    @Test
    @DisplayName("Should throw exception when checking out with insufficient stock")
    void testInsufficientStockValidation() {
        assertThrows(IllegalStateException.class, () ->
                orderService.checkout("user-101", Map.of(testItem.getId(), 10))
        );
    }
}