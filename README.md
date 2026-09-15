# AURA: Architectural Unified Retail Application - CSE Project

**VIT Bhopal University**  
**Course:** Computer Science and Engineering  
**Submitted To:** Faculty Evaluator  
**Student Name:** Chirag Bhatia  
**Registration Number:** 25BAI10766  
**Date of Submission:** September 15, 2026  

---

## Overview

The **Architectural Unified Retail Application (AURA)** is an enterprise-grade, console-based e-commerce platform developed in Java. The project simulates a distributed retail system by decomposing operations into discrete, decoupled domain microservices: User Identity, Product Catalog, Order Fulfillment, Payment Settlement, and Notifications.

This project demonstrates core concepts of modern software engineering, including **Domain-Driven Design (DDD)**, **Saga orchestration with compensating transactions**, **thread-safe concurrency control**, and **asynchronous decoupled event streaming** via a virtual-thread-backed domain event bus. An interactive, terminal-based gateway renders real-time ASCII data views for seamless customer and administrative interactions.

---

## Project Objectives

- Implement a decoupled, scalable **microservices-based retail engine** using Java 21
- Demonstrate proficiency in **object-oriented programming (OOP)** and domain encapsulation
- Utilize **concurrent data structures** (`ConcurrentHashMap`, `CopyOnWriteArrayList`) for consistent multi-threaded state management
- Design an **asynchronous event broker** (`EventBus`) leveraging Java virtual threads
- Create a resilient **transaction rollback mechanism (Compensating Transactions)** for failed checkouts
- Construct an **interactive menu-driven CLI interface** with structured ASCII data tables
- Implement strict **defensive validation mechanisms** to guarantee data integrity across services
- Validate platform reliability and edge-case behaviors with an automated **JUnit 5 test suite**

---

## Features

### 1. **User Identity & Account Management**
- Registers customers with unique user identifiers and validated email addresses
- Encrypts user credentials using SHA-256 cryptographic digests with Base64 encoding
- Emits asynchronous `USER_REGISTERED` domain events upon completed user setup

### 2. **Product Catalog & Real-Time Stock Control**
- Manages product listings, categorical details, high-precision pricing (`BigDecimal`), and stock counts
- Employs thread-safe synchronized methods to prevent race conditions during concurrent checkouts
- Provides O(1) product lookups and real-time inventory adjustments

### 3. **Transactional Order Saga Pipeline**
- Validates active cart items against real-time catalog stock levels
- Reserves required inventory upfront to eliminate overselling and race conditions
- Charges order totals through an external `PaymentGateway` interface abstraction
- Dispatches compensating transactions to restore deducted inventory if payment authorization fails

### 4. **Asynchronous Notification Daemon**
- Listens for platform domain events on background virtual threads without blocking checkout execution
- Automatically logs simulated customer notifications for user registration, payment failures, and confirmed orders

### 5. **Interactive ASCII Terminal Gateway**
- Renders boxed ASCII data tables for catalog exploration, shopping cart management, and order receipts
- Implements defensive input handling to guard against non-numeric entries, negative values, and invalid UUIDs

---

## Technical Stack

| Component | Details |
|-----------|---------|
| **Programming Language** | Java 21 (LTS) |
| **Language Features** | Records, Virtual Threads, Pattern Matching |
| **Data Structures** | ConcurrentHashMap, CopyOnWriteArrayList, LinkedHashMap |
| **Programming Paradigm** | Object-Oriented Programming (OOP) & Domain-Driven Design (DDD) |
| **Architecture Pattern** | Event-Driven Microservices, Saga / Compensating Transactions |
| **Build & Dependency Tool** | Apache Maven 3.9+ |
| **Testing Framework** | JUnit 5 (Jupiter API & Engine) |
| **User Interface** | Console-Based Menu System (ASCII Tabular) |
| **Persistence Model** | Thread-Safe In-Memory Data Stores |

---

## System Architecture

### Class & Domain Structure

**User Record:**
```
Attributes:

- id: String (Unique User UUID)

- username: String (User handle)

- email: String (Unique email address)

- passwordHash: String (SHA-256 encrypted password digest)
```

**Product Class:**
```
Attributes:

- id: String (Unique Product UUID)

- name: String (Product title)

- price: BigDecimal (Monetary unit value)

- stock: int (Protected by synchronized concurrency locks)
```

**Order Class:**
```
Attributes:

- orderId: String (Unique Order UUID)

- userId: String (Customer UUID)

- items: List (Collection of purchased line items)

- totalAmount: BigDecimal (Aggregated financial total)

- status: OrderStatus (PENDING, CONFIRMED, REJECTED, SHIPPED)

- createdAt: Instant (Order placement timestamp)
```
