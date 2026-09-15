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

```
[ Interactive CLI Presentation Gateway ]
               │
┌──────────────┼──────────────┐
▼              ▼              ▼
[UserService] [CatalogService] [OrderService]
│              │              │
│ (Auth Evt)   │ (Stock Lock) │ (Checkout Saga)
└──────────────┴──────┬───────┴──────┘
                      ▼
         [ Asynchronous EventBus ]
           (Virtual Thread Workers)
              │
┌─────────────┴─────────────┐
▼                           ▼
[PaymentService]          [NotificationService]
```
### 4.2 Key Components

**1. Presentation Layer (CLI Gateway)**
- `ECommerceCLI`: Interactive console gateway managing user input loops, session states, and ASCII data tables

**2. Domain Microservices**
- `UserService`: Manages customer registration, credential verification, and SHA-256 password hashing
- `CatalogService`: Oversees catalog inventory, pricing metadata, and atomic stock mutations
- `OrderService`: Orchestrates the checkout saga, stock pre-checks, deductions, and compensating rollbacks
- `PaymentService`: Dispatches transaction charges against the `PaymentGateway` abstraction
- `NotificationService`: Subscribes to domain events and dispatches asynchronous simulated emails

**3. Infrastructure & Concurrency**
- `EventBus`: Thread-safe, virtual-thread event dispatcher decoupling microservices
- `DomainEvent`: Immutable record encapsulating event types, payloads, timestamps, and correlation IDs
- In-Memory Repositories: Thread-safe data stores utilizing `ConcurrentHashMap`

### 4.3 System Specifications

| Specification | Value |
|---------------|-------|
| Target Runtime | Java 21 (LTS) |
| Concurrency Model | Virtual Threads (`Executors.newVirtualThreadPerTaskExecutor()`) |
| Data Storage | In-Memory Concurrent Collections |
| Stock Mutex Type | Synchronized Monitors |
| Hash Algorithm | SHA-256 with Base64 Encoding |
| Identifier Scheme | 128-bit UUID (Universally Unique Identifier) |
| Order Lookup Time | O(1) Constant Time |
| Catalog Lookup Time | O(1) Constant Time |

---

## 5. Technical Implementation

### 5.1 Technology Stack

| Component | Technology |
|-----------|-----------|
| Programming Language | Java 21 (LTS) |
| Concurrency Primitives | Virtual Threads, Synchronized Locks |
| Data Structures | ConcurrentHashMap, CopyOnWriteArrayList, LinkedHashMap |
| Architecture Pattern | Microservices, Event-Driven, Saga Orchestration |
| Build Tool | Apache Maven 3.9+ |
| Testing Framework | JUnit 5 (Jupiter Engine) |
| User Interface | Interactive Terminal CLI (Boxed ASCII Tables) |
| Storage Medium | RAM (In-Memory Thread-Safe State) |

### 5.2 Core Data Structures

**User Record:**
```java
public record User(String id, String username, String email, String passwordHash) {}
```

### Product Class:

```Java
public class Product {
    private final String id;
    private final String name;
    private final BigDecimal price;
    private int stock; // Guarded by synchronized atomic accessors
}
```

### Order Class:

```Java
public class Order {
    private final String orderId;
    private final String userId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private OrderStatus status; // PENDING, CONFIRMED, REJECTED, SHIPPED
    private final Instant createdAt;
}
```

### DomainEvent Record:

```Java
public record DomainEvent(String eventType, String payload, Instant timestamp, String correlationId) {}
```

## 5.3 System Operations
### Operation 1: Register User

- Time Complexity: O(1)

- Space Complexity: O(1)

- **Functionality**: Validates inputs, hashes password via SHA-256, stores user, and publishes USER_REGISTERED

### Operation 2: List Product Catalog

- Time Complexity: O(n)

- Space Complexity: O(n)

- **Functionality**: Reads products from thread-safe map and formats data into structured ASCII tables

### Operation 3: Atomic Stock Deduction

- Time Complexity: O(1)

- Space Complexity: O(1)

- **Functionality**: Thread-safe synchronized check and deduction preventing inventory overselling

### Operation 4: Checkout Saga (Reserve, Charge, Confirm)

- Time Complexity: O(m) where m is distinct cart line items

- Space Complexity: O(m)

- **Functionality**: Validates stock, reserves units, attempts payment, and publishes ORDER_CONFIRMED

### Operation 5: Compensating Transaction (Payment Decline Rollback)

- Time Complexity: O(m) where m is distinct cart line items

- Space Complexity: O(1)

- **Functionality**: Restores reserved inventory back to catalog and publishes ORDER_REJECTED

### Operation 6: Asynchronous Event Dispatch

- Time Complexity: O(k) where k is registered listeners

- Space Complexity: O(1) per task

- **Functionality**: Dispatches listener execution onto independent Java virtual threads

## 6. Features and Functionality
### 6.1 Core Features
### Feature 1: User Identity & Credential Protection

- Onboards new users with distinct email addresses and handles

- Secures credentials using SHA-256 cryptographic hashing

- Dispatches event-driven notifications upon registration

### Feature 2: Real-Time Catalog & Concurrency Control

- Maintains item metadata, financial pricing (BigDecimal), and stock units

- Employs atomic synchronization locks to guarantee inventory consistency under concurrent access

- Delivers instant O(1) item lookups by UUID

### Feature 3: Shopping Cart Management

- Tracks selected product quantities across the user's active session

- Dynamically validates requested quantities against available stock

- Calculates line-item subtotals and grand totals in real time

### Feature 4: Resilient Checkout Saga Orchestration

- Executes a multi-stage transactional pipeline: Pre-check → Reserve → Charge → Settle

- Handles payment gateway integrations gracefully

- Guarantees zero orphaned reservations through automated compensating rollbacks

### Feature 5: Asynchronous Notification Daemon

- Listens to domain topics (USER_REGISTERED, ORDER_CONFIRMED, ORDER_REJECTED)

- Executes asynchronously on virtual threads, ensuring notifications never block checkout response time

- Outputs simulated outbox logs for transaction traceability

### Feature 6: Order Auditing & Lookup

- Persists all executed orders with status lifecycle tracking

- Retrieves itemized breakdowns, total billed amounts, and customer IDs by Order UUID

### Feature 7: Defensive Console Interface

- Renders boxed ASCII data tables for catalog, cart, and receipts

- Encapsulates input prompts in defensive parsing loops, rejecting non-numeric and out-of-bounds choices

---

### 6.2 User Interactions
```
User Flow:
START → Bootstrap Microservices & Seed Catalog
           ↓
     Display Main Menu
           ↓
     User Makes Choice
           ├→ [1] Register User → Input Credentials → Validate → Save → Emit Event
           ├→ [2] Browse Catalog → Retrieve Items → Render ASCII Table
           ├→ [3] Add to Cart → Validate ID & Stock → Update Session Cart
           ├→ [4] View Cart → Compute Subtotals → Display Itemized Table
           ├→ [5] Checkout → Reserve Stock → Attempt Payment
           │       ├── Success → Mark CONFIRMED → Emit Event → Clear Cart → Print Receipt
           │       └── Decline → Execute Rollback → Mark REJECTED → Restore Stock
           ├→ [6] Track Order → Input UUID → Display Status & Items
           ├→ [7] Logout Current User → Clear Active Session & Cart
           └→ [0] Exit Application → Terminate Process
           ↓
     Return to Main Menu (except Exit)
```

---

## 7. Project Significance
### 7.1 Educational Value
This project provides deep, practical experience in:

- **Enterprise Microservices Architecture**: Understanding domain decomposition, service boundaries, and loose coupling

- **Asynchronous Event-Driven Design**: Implementing publish-subscribe event brokers using modern virtual threads

- **Distributed Transaction Management**: Designing Saga patterns with compensating actions to handle partial failures

- **Thread Safety & Concurrency**: Eliminating race conditions on shared state with concurrent collections and monitor locks

- **Automated Verification**: Writing unit tests with JUnit 5 to validate business logic and transaction integrity

### 7.2 Real-World Applicability
The architectural patterns developed in AURA directly apply to:

- Enterprise e-commerce platforms (Amazon, Flipkart)

- Financial payment routing and clearing gateways

- High-concurrency flight and hotel booking engines

- Food delivery dispatch and order aggregation platforms

- Distributed inventory and supply chain tracking systems

- Event-driven microservices backends across fintech and retail

### 7.3 Scalability and Extension
The modular architecture enables seamless horizontal and vertical scaling:

- Replace in-memory maps with distributed SQL/NoSQL databases (PostgreSQL, MongoDB)

- Swap the in-process EventBus with distributed streaming brokers (Apache Kafka, RabbitMQ)

- Expose service methods as Spring Boot RESTful APIs with OpenAPI specifications

- Containerize discrete services into independent Docker images managed by Kubernetes

- Integrate external payment APIs (Stripe, Razorpay, PayPal) via the PaymentGateway interface

##8. Methodology
### 8.1 Development Approach
### Phase 1: Domain Modeling & Requirements Analysis

- Defined functional boundaries across User, Catalog, Order, Payment, and Notification domains

- Formalized non-functional metrics: Reliability, Concurrency Control, Modularity, and Fault Tolerance

### Phase 2: Architectural & Concurrency Design

- Designed the asynchronous virtual-thread EventBus

- Modeled atomic synchronization methods inside the Product entity

- Formulated the checkout saga flowchart and compensating rollback logic

### Phase 3: Core Implementation

-Developed domain entities, records, and thread-safe repository stores

- Coded the transactional OrderService and decoupled PaymentService

- Implemented background event daemons inside NotificationService

### Phase 4: Presentation Layer & Input Defense

- Built ECommerceCLI featuring ASCII tabular views

- Implemented input defense loops trapping NumberFormatException and range errors

### Phase 5: Automated Testing & Verification

- Authored JUnit 5 tests covering successful checkouts, stock exhaustion, and payment rollbacks

- Verified clean build execution across Maven and raw javac compilation

### 8.2 Quality Assurance
### Testing Strategy:

- Unit testing for domain logic validation (OrderServiceTest)

- Compensating transaction verification under simulated payment failure

- Concurrency validation for synchronized stock deductions

- Boundary testing for console input validation routines

### Code Quality:

- Adhered strictly to Java SE 21 standards

- Employed immutable records for events and line items

- Utilized descriptive domain-driven naming conventions

- Implemented comprehensive exception hierarchy handling

## 9. Expected Outcomes
### 9.1 Learning Outcomes
Upon completion, students will be able to:

- ✓ Architect decoupled microservice backends in Java 21

- ✓ Implement asynchronous event-driven pipelines using Java Virtual Threads

- ✓ Prevent race conditions on shared mutable state using thread-safe data structures

- ✓ Design and execute distributed Saga patterns with compensating transaction rollbacks

- ✓ Build defensive, validated console interfaces with tabular ASCII presentations

- ✓ Write automated unit tests verifying transaction integrity under failure scenarios

- ✓ Author complete, publication-grade academic software documentation

## 9.2 Deliverables
### Code Artifacts:

- Complete Java source code across 7 modular packages (com.ecommerce.*)

- Automated test suite (OrderServiceTest.java)

- Maven build configuration (pom.xml) and .gitignore

### Documentation Artifacts:

- README.md (Project overview, feature breakdown, setup and run instructions)

- statement.md (Formal problem statement, scope, target audience, and features)

### Supporting Materials:

- Architecture diagrams and component interaction flows

- Pseudocode specifications for all domain services

- ASCII screen execution scenarios and test execution reports

---

- **Submission Date**: September 15, 2026

- **Student**: Chirag Bhatia (25BAI10766)

- **Course**: CSE - Computer Science and Engineering

- **University**: VIT Bhopal University
