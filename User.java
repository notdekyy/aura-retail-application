package com.ecommerce.user;

public record User(String id, String username, String email, String passwordHash) {}