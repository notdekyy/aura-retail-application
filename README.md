# AURA: Architectural Unified Retail Application - CSE Project

**VIT Bhopal University**  
**Course:** Computer Science and Engineering  
**Submitted To:** Dr. Manorma Chouhan 

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
```

### Data Storage & Concurrency
- **ConcurrentHashMap<String, User>**: Thread-safe storage for registered accounts
- **ConcurrentHashMap<String, Product>**: In-memory catalog supporting concurrent read and atomic stock deductions
- **ConcurrentHashMap<String, Order>**: Order ledger mapping order UUIDs to order entities
- **CopyOnWriteArrayList<Consumer<DomainEvent>>**: Thread-safe subscriber lists inside the asynchronous `EventBus`

---

## Complete Source Code & Implementation

### 1. Build Configuration (`pom.xml`)
```xml
<project xmlns="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0)"
         xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
         xsi:schemaLocation="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0) [http://maven.apache.org/xsd/maven-4.0.0.xsd](http://maven.apache.org/xsd/maven-4.0.0.xsd)">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ecommerce</groupId>
    <artifactId>aura-retail-application</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>com.ecommerce.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>


- createdAt: Instant (Order placement timestamp)
```
## How to Run

### Prerequisites
- Java Development Kit (JDK) 21 or higher installed on your system

### Execution Steps

```bash
mkdir bin
dir /s /B src\main\java\*.java > sources.txt
javac -d bin @sources.txt
del sources.txt
java -cp bin com.ecommerce.Main
```

### Follow the On-Screen Menu:
- Enter `1` to register a new user account

- Enter `2` to view product inventory in ASCII table layout

- Enter `3` to add available items to your session cart

- Enter `4` to inspect the cart total and calculate subtotals

- Enter `5` to run checkout and trigger payment settlement

- Enter `6` to look up an order status by ID

- Enter `7` to log out of the current session

- Enter `0` to exit the application

## Pseudocode

### Asynchronous Event Broker
```
ALGORITHM EventBus.publish(event)
    listeners ← listenerMap.get(event.eventType)
    FOR EACH listener IN listeners DO
        SUBMIT listener.accept(event) TO VirtualThreadExecutor
    END FOR
END ALGORITHM
```

### Atomic Inventory Reservation
```
ALGORITHM Product.deductStock(quantity)
    SYNCHRONIZED BLOCK
        IF quantity <= 0 OR stock < quantity THEN
            RETURN FALSE
        END IF
        stock ← stock - quantity
        RETURN TRUE
    END SYNCHRONIZED BLOCK
END ALGORITHM
ALGORITHM Product.addStock(quantity)
    SYNCHRONIZED BLOCK
        IF quantity > 0 THEN
            stock ← stock + quantity
        END IF
    END SYNCHRONIZED BLOCK
END ALGORITHM
```

### User Registration Function
```
ALGORITHM registerUser(username, email, rawPassword)
    IF username IS BLANK OR email DOES NOT CONTAIN "@" OR LENGTH(rawPassword) < 6 THEN
        THROW IllegalArgumentException
    END IF
    
    IF userRepository.findByEmail(email) EXISTS THEN
        THROW IllegalStateException("Email already registered")
    END IF
    
    hash ← SHA256(rawPassword)
    user ← NEW User(GENERATE_UUID(), username, email, hash)
    userRepository.save(user)
    
    EventBus.publish(NEW DomainEvent("USER_REGISTERED", user.id))
    RETURN user
END ALGORITHM
```

### Transactional Checkout Saga (Order Service)
```
ALGORITHM checkout(userId, cartItems)
    IF cartItems IS EMPTY THEN
        THROW IllegalArgumentException("Cart cannot be empty")
    END IF

    // Step 1: Inventory Pre-Check
    FOR EACH (productId, requestedQty) IN cartItems DO
        product ← catalogService.getProduct(productId)
        IF product.getStock() < requestedQty THEN
            THROW IllegalStateException("Insufficient inventory for product: " + product.name)
        END IF
    END FOR

    // Step 2: Atomic Stock Reservation
    FOR EACH (productId, requestedQty) IN cartItems DO
        product ← catalogService.getProduct(productId)
        product.deductStock(requestedQty)
    END FOR

    // Step 3: Record Pending Order
    order ← NEW Order(GENERATE_UUID(), userId, cartItems)
    orderRepository.save(order)
    EventBus.publish(NEW DomainEvent("ORDER_CREATED", order.id))

    // Step 4: Process Payment & Handle Failure Rollback
    paymentApproved ← paymentService.charge(order.id, order.totalAmount)

    IF paymentApproved == TRUE THEN
        order.setStatus(CONFIRMED)
        EventBus.publish(NEW DomainEvent("ORDER_CONFIRMED", order.id))
    ELSE
        // Compensating Transaction: Roll back allocated inventory
        FOR EACH (productId, requestedQty) IN cartItems DO
            product ← catalogService.getProduct(productId)
            product.addStock(requestedQty)
        END FOR
        order.setStatus(REJECTED)
        EventBus.publish(NEW DomainEvent("ORDER_REJECTED", order.id))
    END IF

    RETURN order
END ALGORITHM
```

### Track Order Function
```
ALGORITHM trackOrder(orderId)
    order ← orderService.getOrder(orderId)
    
    IF order DOES NOT EXIST THEN
        PRINT "No order located with ID"
        RETURN
    END IF
    
    PRINT "Order Reference: ", order.orderId
    PRINT "Customer ID: ", order.userId
    PRINT "Status: ", order.status
    PRINT "Total Amount: ", order.totalAmount
    FOR EACH item IN order.items DO
        PRINT item.productId, item.quantity, item.unitPrice
    END FOR
END ALGORITHM
```

### Main Application Loop
```
ALGORITHM main()
    initializeServices()
    seedProductCatalog()
    choice ← -1

    WHILE choice ≠ 0 DO
        displayMainMenu()
        INPUT choice

        SWITCH choice
            CASE 1:
                registerUser()
            CASE 2:
                displayCatalog()
            CASE 3:
                addToCart()
            CASE 4:
                displayCart()
            CASE 5:
                checkout()
            CASE 6:
                trackOrder()
            CASE 7:
                logout()
            CASE 0:
                PRINT "Exiting AURA Platform..."
            DEFAULT:
                PRINT "Invalid choice. Please select 0-7."
        END SWITCH
    END WHILE
END ALGORITHM
```

---

## System Workflow

### Main Application Flow
```
START
   │
   ▼
Initialize Services & Background Daemons (Virtual-Thread EventBus)
   │
   ▼
Seed Catalog with Sample Hardware Products
   │
   ▼
Display Main Console Menu
   │
   ▼
User Makes Selection
   ├─→ Option 1: Register User → Input Details → Hash Password → Save Account → Emit Event → Return to Menu
   ├─→ Option 2: Browse Catalog → Query Repository → Render ASCII Box Table → Return to Menu
   ├─→ Option 3: Add to Cart → Validate Product ID & Stock → Add to Session Cart → Return to Menu
   ├─→ Option 4: View Cart → Calculate Totals & Subtotals → Print Summary Table → Return to Menu
   ├─→ Option 5: Checkout & Pay → Check Stock → Deduct Inventory → Attempt Payment
   │              ├─→ Success: Mark CONFIRMED → Emit Event → Clear Cart → Print Receipt → Return to Menu
   │              └─→ Failure: Roll Back Stock (Compensating Transaction) → Mark REJECTED → Return to Menu
   ├─→ Option 6: Track Order → Input UUID → Display Order Line Items & Status → Return to Menu
   ├─→ Option 7: Logout User → Reset Session Identity & Clear Active Cart → Return to Menu
   └─→ Option 0: Exit → Terminate Application → END
   │
   ▼
Loop Until User Chooses Exit (Option 0)
```

## Flowchart

```
                        ┌─────────────────────┐
                        │        START        │
                        └──────────┬──────────┘
                                   │
                        ┌──────────▼──────────┐
                        │ Initialize Services │
                        │  & Event Listeners  │
                        └──────────┬──────────┘
                                   │
                        ┌──────────▼──────────┐
                        │    Display Menu     │
                        │   & Active Status   │
                        └──────────┬──────────┘
                                   │
        ┌──────────────────────────┼──────────────────────────┐
        │                          │                          │
   ┌────▼────┐                ┌────▼────┐                ┌────▼────┐
   │Option 1 │                │Option 2 │                │Option 3 │
   │Register │                │ Browse  │                │ Add to  │
   │  User   │                │ Catalog │                │  Cart   │
   └────┬────┘                └────┬────┘                └────┬────┘
        │                          │                          │
   ┌────▼───────────┐         ┌────▼───────────┐         ┌────▼───────────┐
   │ Valid Details? │         │  Render ASCII  │         │ Stock Avail?   │
   └────┬─────┬─────┘         │  Catalog Table │         └────┬─────┬─────┘
        │     │               └────┬───────────┘              │     │
      Y │     │ N                  │                        Y │     │ N
        │     ▼                    │                          │     ▼
   ┌────▼────┐┌───────────┐        │                     ┌────▼────┐┌───────────┐
   │Save Hash││Display Err│        │                     │Update   ││Display Err│
   │Emit Evt │└─────┬─────┘        │                     │Cart Map │└─────┬─────┘
   └────┬────┘      │              │                     └────┬────┘      │
        │           │              │                          │           │
        └─────┬─────┘              │                          └─────┬─────┘
              │                    │                                │
              └────────────────────┼────────────────────────────────┘
                                   │
        ┌──────────────────────────┼──────────────────────────┐
        │                          │                          │
   ┌────▼────┐                ┌────▼────┐                ┌────▼────┐
   │Option 4 │                │Option 5 │                │Option 6 │
   │View Cart│                │Checkout │                │  Track  │
   │& Totals │                │ Pipeline│                │  Order  │
   └────┬────┘                └────┬────┘                └────┬────┘
        │                          │                          │
   ┌────▼───────────┐         ┌────▼───────────┐         ┌────▼───────────┐
   │ Display ASCII  │         │ Deduct Stock & │         │ Order Exists?  │
   │ Receipt Table  │         │ Charge Gateway │         └────┬─────┬─────┘
   └────┬───────────┘         └────┬─────┬─────┘              │     │
        │                          │     │                  Y │     │ N
        │                Approved  │     │ Declined           │     ▼
        │                     ┌────▼─┐ ┌─▼────────┐      ┌────▼────┐┌───────────┐
        │                     │Mark  │ │Roll Back │      │Show Item││Display Err│
        │                     │CONF. │ │Stock     │      │Receipt  │└─────┬─────┘
        │                     └────┬─┘ └─┬────────┘      └────┬────┘      │
        │                          │     │                    │           │
        │                          └─────┼────────────────────┴───────────┘
        │                                │
        └────────────────────────────────┼────────────────────────────────┐
                                         │                                │
                              ┌──────────┼──────────┐                     │
                              │          │          │                     │
                         ┌────▼────┐┌────▼────┐┌────▼────┐                │
                         │Option 7 ││Option 0 ││ Options │                │
                         │ Logout  ││  Exit   ││   1-7   │                │
                         │ Session ││ Program ││ Complete│                │
                         └────┬────┘└────┬────┘└────┬────┘                │
                              │          │          │                     │
                              ▼          ▼          ▼                     │
                         ┌─────────────────────────────┐                  │
                         │   Return to Menu / Loop     │◄─────────────────┘
                         └───────────────┬─────────────┘
                                         │
                                ┌────────▼────────┐
                                │       END       │
                                └─────────────────┘
``` 
## Key Data Structures Used

### 1. **ConcurrentHashMap**
- Powers the in-memory persistence layer across UserRepository, ProductRepository, and OrderService

- Provides thread-safe concurrent reads and segment-level locking without freezing operations during writes

### 2. **CopyOnWriteArrayList**
- Retains subscribers inside the EventBus

- Allows safe, lock-free iteration during event dispatches while permitting dynamic subscriber additions

### 3. **LinkedHashMap**
- Maintains active line items in the user's cart (activeCart)

- Preserves insertion order so products are displayed cleanly in the sequence the user added them

---

## Functions Overview

| Function | Purpose | Parameters | Return Type |
|----------|---------|-----------|-------------|
| `registerUser(username, email, password)` | Validate credentials, hash password, and persist user | `String, String, String` | `User` |
| `createProduct(name, price, stock)` | Instantiate and add product to the catalog repository | `String, BigDecimal, int` | `Product` |
| `getProduct(id)` | Retrieve a product by its unique UUID identifier | `String` | `Product` |
| `deductStock(quantity)` | Atomically deduct stock units using synchronized lock | `int` | `boolean` |
| `addStock(quantity)` | Atomically restore stock units during compensation rollback | `int` | `void` |
| `checkout(userId, cartItems)` | Execute order saga, stock deduction, and payment authorization | `String, Map<String, Integer>` | `Order` |
| `charge(orderId, amount)` | Process payment transaction and broadcast outcome events | `String, BigDecimal` | `boolean` |
| `publish(event)` | Dispatch domain event asynchronously across virtual threads | `DomainEvent` | `void` |
| `start()` | Launch the interactive console menu loop | None | `void` |

---

## Data Flow

```
User CLI Input
     ↓
Input Bounds & Type Validation
     ↓
Service Layer Orchestration
     ↓
Atomic In-Memory Mutation (Thread-Safe Repository)
     ↓
Asynchronous Event Publication (Virtual-Thread Bus)
     ↓
Background Daemon Actions (Notifications / Audit Logs)
     ↓
Formatted ASCII Receipt Generation
     ↓
Return to Interactive Menu
```

---

## Error Handling

The system implements robust error handling across all architectural tiers:

- **Input Sanitization**: Traps non-numeric characters using guarded loops, preventing `NumberFormatException` crashes

- **Authentication Guards**: Rejects blank usernames, invalid email address structures (missing `@`), and short passwords

- **Stock Underflow Prevention**: Synchronized operations prevent deductions beyond current inventory levels

- **Compensating Rollbacks**: Automatically returns reserved stock to the catalog if payment authorization is denied

- **Domain Exceptions**: Raises typed exceptions (`NoSuchElementException`, `IllegalStateException`) with informative context messages

---

## Advantages of This Implementation

- **Decoupled Microservices**: Services operate independently and interact cleanly through well-defined service contracts

- **High Concurrency**: Utilizes Java 21 virtual threads and concurrent collections to maximize throughput

- **Zero Overselling**: Synchronized inventory blocks prevent double-allocation race conditions

- **Fault-Tolerant Checkout**: Saga pattern with compensating transactions prevents abandoned deductions upon payment failure

- **Clean Separation of Concerns**: CLI presentation layer remains completely decoupled from core business and domain logic

---

## Limitations and Future Enhancements

### Current Limitations
- **In-Memory Persistence**: Records reset when the application process terminates

- **Mock Payment Gateway**: Operates with a deterministic mock rather than external payment providers

- **Single-Node Event Bus**: Events are confined to a single running JVM instance

---

### Suggested Enhancements
- Add database persistence using Hibernate / Spring Data JPA with PostgreSQL

- Integrate external payment APIs such as Stripe or PayPal

- Transition the in-memory event bus to Apache Kafka or RabbitMQ for distributed messaging

- Package services as independent container images using Docker and Docker Compose

- Implement JWT (JSON Web Token) authentication to secure API boundaries

---

## Testing Recommendations
### Test Cases (`OrderServiceTest.java`)
### Successful Checkout Pipeline

- Reserve stock for a valid catalog product and complete checkout

- Verify that the resulting order status is marked as `CONFIRMED`

- Verify that catalog stock is correctly reduced by the purchased quantity

### Compensating Transaction (Payment Failure)

- Trigger a checkout using a failing payment gateway stub

- Verify that the resulting order status is marked as `REJECTED`

- Verify that catalog stock is fully rolled back to its baseline quantity

### Insufficient Stock Rejection

- Attempt to checkout a quantity greater than current available units

- Verify that an `IllegalStateException` is thrown immediately

- Verify that no inventory deductions or order confirmations take place

---

## Conclusion
The Architectural Unified Retail Application (AURA) project successfully demonstrates modern software engineering and microservices principles in Java 21. By combining decoupled domain boundaries, thread-safe concurrent data management, asynchronous event-driven notifications, and automated rollback sagas, the project provides a resilient foundation for real-world enterprise retail platforms.

## References
- Oracle Java 21 Official Documentation: https://docs.oracle.com/en/java/javase/21/

- Martin Fowler, Patterns of Enterprise Application Architecture

- Enterprise Integration Patterns: Event-Driven Architectures & Compensating Sagas

- JUnit 5 Official User Guide: https://junit.org/junit5/docs/current/user-guide/

---

**Project Submitted**: September 15, 2026

**Academic Year**: 2026-2027

**Semester**: Fall
