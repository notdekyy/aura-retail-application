package com.ecommerce.user;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final Map<String, User> storage = new ConcurrentHashMap<>();

    public Optional<User> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(u -> u.email().equalsIgnoreCase(email))
                .findFirst();
    }

    public void save(User user) {
        storage.put(user.id(), user);
    }
}