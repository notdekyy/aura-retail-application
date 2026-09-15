package com.ecommerce;

import com.ecommerce.catalog.CatalogService;
import com.ecommerce.catalog.ProductRepository;
import com.ecommerce.cli.ECommerceCLI;
import com.ecommerce.notification.NotificationService;
import com.ecommerce.order.OrderService;
import com.ecommerce.payment.PaymentService;
import com.ecommerce.user.UserRepository;
import com.ecommerce.user.UserService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        new NotificationService();

        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);

        ProductRepository productRepo = new ProductRepository();
        CatalogService catalogService = new CatalogService(productRepo);

        PaymentService paymentService = new PaymentService((orderId, amount) -> amount.compareTo(BigDecimal.ZERO) > 0);
        OrderService orderService = new OrderService(catalogService, paymentService);

        catalogService.createProduct("MacBook Pro M3", new BigDecimal("1999.99"), 5);
        catalogService.createProduct("Sony WH-1000XM5 Headphones", new BigDecimal("398.00"), 12);
        catalogService.createProduct("Logitech MX Master 3S Mouse", new BigDecimal("99.99"), 20);
        catalogService.createProduct("Mechanical Keyboard", new BigDecimal("149.50"), 8);

        ECommerceCLI cli = new ECommerceCLI(userService, catalogService, orderService);
        cli.start();
    }
}