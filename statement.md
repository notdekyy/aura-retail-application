# Statement of Purpose and Project Overview

**AURA: Architectural Unified Retail Application – CSE Project**

**Institution:** VIT Bhopal University  
**Department:** Computer Science and Engineering  
**Submitted By:** Chirag Bhatia  
**Registration Number:** 25BAI10766  
**Submitted To:** Faculty Evaluator  
**Date of Submission:** September 15, 2026  
**Academic Year:** 2026-2027

---

## 1. Project Title

**AURA: An Event-Driven, Microservices-Based Scalable Retail Engine with Asynchronous Message Dispatch and Resilient Transaction Sagas**

---

## 2. Executive Summary

The Architectural Unified Retail Application (AURA) is an enterprise-grade Java 21 application engineered to model an e-commerce platform using decoupled microservices architecture. The system decomposes complex retail workflows into discrete, autonomous domains: User Identity & Authentication, Product Catalog & Real-Time Inventory, Transactional Order Fulfillment, Payment Settlement, and Asynchronous Notification Dispatch. Operating over an in-memory virtual-thread event bus, AURA demonstrates key enterprise software design patterns including Domain-Driven Design (DDD), Saga pattern with compensating transaction rollbacks, thread-safe concurrent state management, and defensive console gateway design. This project highlights advanced software engineering principles by maintaining zero coupling across domain boundaries while delivering a robust, interactive CLI interface.

---

## 3. Statement of Purpose

### 3.1 Problem Definition

Traditional monolithic e-commerce platforms suffer from tight structural coupling: an upstream latency spike or fatal exception in a secondary subsystem (such as an external payment provider or email notification dispatcher) can block worker threads and crash the critical checkout pipeline. Furthermore, concurrent operations on shared retail inventories often result in race conditions, phantom reads, and double-selling under high transaction volumes. There exists a clear necessity for an automated, resilient, and decoupled system that can:

- Isolate business domains into autonomous, loosely coupled services
- Guarantee thread-safe, atomic inventory deductions during concurrent user access
- Execute atomic checkout sagas with automated compensating rollbacks upon payment decline
- Offload non-critical side effects (notifications, audit logging) to asynchronous virtual-thread workers
- Ensure strict input validation and defensive error handling across user boundaries

### 3.2 Project Objectives

The primary objectives of the AURA project are:

**Academic Learning Objectives:**

1. **Demonstrate Advanced Object-Oriented Programming (OOP) & DDD**
   - Decompose systems into autonomous domain entities, value objects, and records
   - Enforce domain encapsulation across repository and service layers
   - Apply modern Java 21 idioms including immutable records and pattern matching

2. **Apply Concurrent Data Structures & Concurrency Controls**
   - Implement thread-safe collections (`ConcurrentHashMap`, `CopyOnWriteArrayList`)
   - Utilize method-level synchronization locks to eliminate inventory race conditions
   - Leverage Java 21 Virtual Threads (`Executors.newVirtualThreadPerTaskExecutor()`) for high-throughput I/O

3. **Develop Resilient Distributed Architectural Patterns**
   - Architect an in-memory, publish-subscribe event broker (`EventBus`)
   - Implement the Saga pattern with automated compensating transactions to rollback stock
   - Maintain event payload immutability via standardized `DomainEvent` records

4. **Master Software Engineering Practices**
   - Design clean, layered Maven package structures separating concerns
   - Apply automated unit testing standards using JUnit 5 (Jupiter engine)
   - Follow clean code standards, defensive boundary validation, and meaningful commit structures

5. **Build an Interactive Console Gateway**
   - Construct a user-friendly, menu-driven CLI presentation layer
   - Format tabular data using structured ASCII box-drawing boundaries
   - Guard against terminal input exceptions with resilient validation loops

**Practical Objectives:**

1. Deliver a fully runnable, zero-dependency Java 21 application demonstrating end-to-end retail transactions
2. Ensure 100% data consistency during payment declines through compensating inventory restorations
3. Provide comprehensive technical documentation, architecture flowcharts, and execution guides
4. Establish clean test coverage verifying positive checkout, insufficient inventory, and failure rollbacks

### 3.3 Project Scope

**Functional Scope:**
- Register new users with SHA-256 cryptographic password hashing
- Populate and browse a real-time product catalog with stock and pricing
- Manage active shopping carts with multi-item line reservations
- Execute atomic transactional checkout pipelines
- Simulate payment gateway charging with success and decline scenarios
- Roll back deducted inventory automatically upon payment failure
- Asynchronously dispatch email notifications via event listeners
- Track confirmed and rejected orders using unique UUIDs
- Provide a defensive, interactive ASCII console interface

**Technical Scope:**
- Programming Language: Java 21 (LTS)
- Build System: Apache Maven 3.9+
- Concurrency Engine: Java Virtual Threads (Project Loom)
- Testing Framework: JUnit 5
- Storage: In-memory thread-safe collections
- Target Environments: Windows, macOS, Linux

**Out of Scope:**
- Distributed persistent databases (e.g., PostgreSQL, MongoDB)
- External network-bound payment APIs (Stripe, Razorpay)
- External message brokers (Apache Kafka, RabbitMQ)
- Graphical User Interfaces (JavaFX, Swing) or Web Frontends

---

## 4. System Overview

### 4.1 High-Level Architecture

AURA follows a decoupled, event-driven microservices architecture mediated by an asynchronous Event Bus:
