package com.ecommerce.cli;

import com.ecommerce.catalog.CatalogService;
import com.ecommerce.catalog.Product;
import com.ecommerce.order.Order;
import com.ecommerce.order.OrderItem;
import com.ecommerce.order.OrderService;
import com.ecommerce.user.User;
import com.ecommerce.user.UserService;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class ECommerceCLI {
    private final UserService userService;
    private final CatalogService catalogService;
    private final OrderService orderService;
    private final Scanner scanner;

    private User currentUser = null;
    private final Map<String, Integer> activeCart = new LinkedHashMap<>();

    public ECommerceCLI(UserService userService, CatalogService catalogService, OrderService orderService) {
        this.userService = userService;
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printBanner();
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readIntInput("Select an option [0-7]: ", 0, 7);

            switch (choice) {
                case 1 -> handleRegister();
                case 2 -> displayCatalog();
                case 3 -> handleAddToCart();
                case 4 -> displayCart();
                case 5 -> handleCheckout();
                case 6 -> handleTrackOrder();
                case 7 -> handleLogout();
                case 0 -> {
                    System.out.println("\n[+] Exiting AURA Platform. Goodbye!");
                    running = false;
                }
            }
        }
    }

    private void printBanner() {
        System.out.println("=================================================================");
        System.out.println("            AURA: Architectural Unified Retail Application       ");
        System.out.println("               Event-Driven Microservices Gateway                ");
        System.out.println("=================================================================");
    }

    private void printMainMenu() {
        String sessionStatus = (currentUser == null) ? "GUEST" : currentUser.username() + " (" + currentUser.email() + ")";
        int cartItemsCount = activeCart.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("\n-------------------------------------------------------------");
        System.out.printf(" ACTIVE SESSION: %-25s | CART ITEMS: %d%n", sessionStatus, cartItemsCount);
        System.out.println("-------------------------------------------------------------");
        System.out.println(" [1] Register New User");
        System.out.println(" [2] Browse Product Catalog");
        System.out.println(" [3] Add Product to Cart");
        System.out.println(" [4] View Cart & Totals");
        System.out.println(" [5] Checkout & Process Payment");
        System.out.println(" [6] Track Order by ID");
        System.out.println(" [7] Logout Current User");
        System.out.println(" [0] Exit Application");
        System.out.println("-------------------------------------------------------------");
    }

    private void handleRegister() {
        System.out.println("\n--- [User Registration] ---");
        String username = readStringInput("Enter username: ", false);
        String email = readStringInput("Enter email: ", false);
        String password = readStringInput("Enter password (min 6 chars): ", false);

        try {
            currentUser = userService.registerUser(username, email, password);
            System.out.printf("[✓] Registration successful! Welcome, %s.%n", currentUser.username());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.printf("[X] Registration Failed: %s%n", ex.getMessage());
        }
    }

    private void displayCatalog() {
        List<Product> products = catalogService.listAll();
        System.out.println("\n+--------------------------------------+---------------------------+-------------+-------+");
        System.out.printf("| %-36s | %-25s | %-11s | %-5s |%n", "Product ID", "Name", "Price (USD)", "Stock");
        System.out.println("+--------------------------------------+---------------------------+-------------+-------+");

        if (products.isEmpty()) {
            System.out.println("|                      Catalog is currently empty.                          |");
        } else {
            for (Product p : products) {
                System.out.printf("| %-36s | %-25s | $%10.2f | %-5d |%n",
                        p.getId(),
                        truncate(p.getName(), 25),
                        p.getPrice(),
                        p.getStock());
            }
        }
        System.out.println("+--------------------------------------+---------------------------+-------------+-------+");
    }

    private void handleAddToCart() {
        displayCatalog();
        String productId = readStringInput("Enter Product ID to add: ", false);

        Product product;
        try {
            product = catalogService.getProduct(productId);
        } catch (NoSuchElementException e) {
            System.out.println("[X] Error: Product not found.");
            return;
        }

        int currentCartQty = activeCart.getOrDefault(productId, 0);
        int availableStock = product.getStock() - currentCartQty;

        if (availableStock <= 0) {
            System.out.println("[!] Cannot add more. All available stock is already in your cart or sold out.");
            return;
        }

        int quantity = readIntInput("Enter quantity (1 to " + availableStock + "): ", 1, availableStock);
        activeCart.put(productId, currentCartQty + quantity);
        System.out.printf("[✓] Added %d x '%s' to cart.%n", quantity, product.getName());
    }

    private void displayCart() {
        System.out.println("\n--- [Your Shopping Cart] ---");
        if (activeCart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("+---------------------------+-------+-------------+-------------+");
        System.out.printf("| %-25s | %-5s | %-11s | %-11s |%n", "Product Name", "Qty", "Unit Price", "Subtotal");
        System.out.println("+---------------------------+-------+-------------+-------------+");

        BigDecimal grandTotal = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> entry : activeCart.entrySet()) {
            Product p = catalogService.getProduct(entry.getKey());
            int qty = entry.getValue();
            BigDecimal subtotal = p.getPrice().multiply(BigDecimal.valueOf(qty));
            grandTotal = grandTotal.add(subtotal);

            System.out.printf("| %-25s | %-5d | $%10.2f | $%10.2f |%n",
                    truncate(p.getName(), 25), qty, p.getPrice(), subtotal);
        }
        System.out.println("+---------------------------+-------+-------------+-------------+");
        System.out.printf("| TOTAL ESTIMATED AMOUNT:                     $%12.2f |%n", grandTotal);
        System.out.println("+---------------------------------------------------------+");
    }

    private void handleCheckout() {
        if (currentUser == null) {
            System.out.println("[!] Authentication required: Please register or log in first (Option 1).");
            return;
        }

        if (activeCart.isEmpty()) {
            System.out.println("[!] Your cart is empty. Add items before checking out.");
            return;
        }

        displayCart();
        String confirm = readStringInput("Proceed with checkout and charge payment? (y/N): ", true);
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("[-] Checkout cancelled.");
            return;
        }

        System.out.println("\n[i] Initiating checkout saga across microservices...");
        try {
            Order order = orderService.checkout(currentUser.id(), activeCart);
            System.out.println("\n================ ORDER RECEIPT ================");
            System.out.printf(" Order ID   : %s%n", order.getOrderId());
            System.out.printf(" Customer ID: %s%n", order.getUserId());
            System.out.printf(" Status     : %s%n", order.getStatus());
            System.out.printf(" Final Total: $%s%n", order.getTotalAmount());
            System.out.println("===============================================");

            activeCart.clear();
        } catch (Exception ex) {
            System.out.printf("[X] Transaction Aborted: %s%n", ex.getMessage());
        }
    }

    private void handleTrackOrder() {
        String orderId = readStringInput("Enter Order UUID: ", false);
        Optional<Order> orderOpt = orderService.getOrder(orderId);

        if (orderOpt.isEmpty()) {
            System.out.println("[X] No order located with ID: " + orderId);
            return;
        }

        Order order = orderOpt.get();
        System.out.println("\n+-------------------------------------------------------------+");
        System.out.println("|                       ORDER DETAILS                         |");
        System.out.println("+-------------------------------------------------------------+");
        System.out.printf("| Order Reference: %-42s |%n", order.getOrderId());
        System.out.printf("| Placed By User : %-42s |%n", order.getUserId());
        System.out.printf("| Current Status : %-42s |%n", order.getStatus());
        System.out.printf("| Total Billed   : $%-41.2f |%n", order.getTotalAmount());
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("| Items Ordered:                                              |");
        for (OrderItem item : order.getItems()) {
            System.out.printf("|  - Product [%s] x %d @ $%s each%n",
                    item.productId(), item.quantity(), item.unitPrice());
        }
        System.out.println("+-------------------------------------------------------------+");
    }

    private void handleLogout() {
        if (currentUser == null) {
            System.out.println("[!] No active session to logout.");
            return;
        }
        System.out.printf("[✓] User %s signed out.%n", currentUser.username());
        currentUser = null;
        activeCart.clear();
    }

    private int readIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("[!] Value must be between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Please enter a valid number.");
            }
        }
    }

    private String readStringInput(String prompt, boolean allowBlank) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (allowBlank || !input.isEmpty()) {
                return input;
            }
            System.out.println("[!] Field cannot be empty.");
        }
    }

    private String truncate(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}