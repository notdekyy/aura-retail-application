package com.ecommerce.user;

import com.ecommerce.common.DomainEvent;
import com.ecommerce.common.EventBus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User registerUser(String username, String email, String rawPassword) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address format");
        }
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Password must contain at least 6 characters");
        }
        if (repository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("An account with this email already exists");
        }

        String hash = hashPassword(rawPassword);
        User user = new User(UUID.randomUUID().toString(), username.trim(), email.trim(), hash);
        repository.save(user);

        EventBus.getInstance().publish(new DomainEvent("USER_REGISTERED", user.id()));
        return user;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encoded);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 cryptographic algorithm unavailable", e);
        }
    }
}