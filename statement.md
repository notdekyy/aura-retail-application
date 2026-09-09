\# Project Statement: AURA (Architectural Unified Retail Application)



\## 1. Problem Statement

Monolithic e-commerce backends suffer from tight architectural coupling: failure in a secondary service (such as payment processing or email dispatch) can stall or corrupt the critical checkout pipeline. Furthermore, concurrent operations on shared inventory frequently lead to race conditions, phantom stock, and double-selling. There is an imperative need for a modular, event-driven e-commerce engine that decouples domain boundaries, guarantees atomic inventory reservations, and provides automated compensating transaction rollbacks upon upstream failure.



\## 2. Scope of the Project

AURA provides a decoupled backend architecture implemented in pure Java:

\- Customer registration and input validation using cryptographic hashing (SHA-256).

\- Real-time catalog and thread-safe inventory tracking with synchronized atomic guards.

\- A multi-step transactional checkout pipeline handling inventory reservation, payment authorization, and automatic compensating rollbacks if payment fails.

\- An in-memory, virtual-thread-based Event Bus enabling asynchronous notification dispatch.

\- A robust presentation CLI with box-drawing ASCII data tables, session state, and defensive input parsing.



\## 3. Target Users

\- \*\*E-Commerce Shoppers:\*\* End users searching the catalog, aggregating products into an active shopping cart, and checking out.

\- \*\*Store Administrators \& Inventory Managers:\*\* Operators monitoring stock levels, order statuses, and price records.

\- \*\*Academic Evaluators \& Software Engineers:\*\* Technical reviewers inspecting decoupled microservice patterns, concurrency management, and unit testing practices.



\## 4. High-Level Features

\- \*\*Decoupled Event Bus:\*\* Asynchronous event broker dispatching `DomainEvent` objects on virtual threads.

\- \*\*Atomic Stock Reservation:\*\* Concurrency-guarded inventory methods preventing stock underflows.

\- \*\*Compensating Rollback Saga:\*\* Automatic stock recovery triggered when a payment gateway declines a charge.

\- \*\*Defensive CLI Presentation Layer:\*\* Guarded input loops enforcing type safety, value ranges, and clear tabular displays.

