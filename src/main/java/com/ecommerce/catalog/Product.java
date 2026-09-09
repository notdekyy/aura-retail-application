package com.ecommerce.catalog;

import java.math.BigDecimal;

public class Product {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private int stock;

    public Product(String id, String name, BigDecimal price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public synchronized int getStock() {
        return stock;
    }

    public synchronized boolean deductStock(int quantity) {
        if (quantity <= 0 || stock < quantity) {
            return false;
        }
        stock -= quantity;
        return true;
    }

    public synchronized void addStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }
}