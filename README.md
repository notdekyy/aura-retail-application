# AURA: Architectural Unified Retail Application



A modular, event-driven e-commerce backend built with Java 21, demonstrating clean domain-driven design, thread-safe inventory management, and automated transaction rollbacks.



---



## 1. Project Overview

AURA models an enterprise retail platform by isolating distinct microservices—User, Product Catalog, Order Fulfillment, Payment, and Notification—and integrating them through an asynchronous Event Bus.



---



## 2. Features

- \*\*Event-Driven Microservices:\*\* Asynchronous messaging via `EventBus` powered by Java virtual threads.

- \*\*Compensating Transaction Saga:\*\* Automatic inventory rollback if payment processing encounters an error or rejection.

- \*\*Thread-Safe Inventory Control:\*\* Synchronized inventory methods preventing overselling and race conditions.

- \*\*Interactive Terminal UI:\*\* Complete CLI with box-drawing ASCII borders for catalog browsing, cart tracking, and purchase receipts.

- \*\*Automated Test Suite:\*\* Built-in JUnit 5 tests covering positive checkout, insufficient inventory errors, and rollback logic.



---



## 3. Technologies Used

- \*\*Language:\*\* Java 21

- \*\*Build Tool:\*\* Apache Maven 3.9+

- \*\*Testing:\*\* JUnit 5 (Jupiter)

- \*\*Version Control:\*\* Git



---



## 4. Steps to Install \& Run



### Prerequisites

- JDK 21+ installed (`java -version`)

- Apache Maven installed (`mvn -v`)



### Build the Project

```bash

mvn clean compile

