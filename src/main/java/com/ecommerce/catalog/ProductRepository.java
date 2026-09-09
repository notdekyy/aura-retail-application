package com.ecommerce.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepository {
    private final Map<String, Product> catalog = new ConcurrentHashMap<>();

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(catalog.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(catalog.values());
    }

    public void save(Product product) {
        catalog.put(product.getId(), product);
    }
}