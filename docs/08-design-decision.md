## Introduction

This document records the major architectural and technical decisions made during the development of SnapURL.

The purpose of this document is to explain the reasoning behind these decisions, their benefits, trade-offs, and potential future alternatives.

---

## Decision 1: Java as the Programming Language

### Decision

Java was selected as the primary programming language for the application.

### Reasoning

- Strong object-oriented programming support.
- Mature ecosystem and tooling.
- Excellent integration with Spring Framework.
- Widely used in enterprise applications.
- Strong community support.

### Trade-Offs

Pros:

- Reliable and scalable.
- Large developer community.
- Rich ecosystem.

Cons:

- More verbose compared to some modern languages.
- Higher memory consumption compared to lightweight alternatives.

---

## Decision 2: Spring Boot Framework

### Decision

Spring Boot was chosen as the backend framework.

### Reasoning

- Rapid application development.
- Built-in dependency injection.
- Easy integration with Spring Security and JPA.
- Production-ready features.
- Reduced boilerplate configuration.

### Trade-Offs

Pros:

- Faster development.
- Industry-standard framework.
- Excellent documentation.

Cons:

- Learning curve for beginners.
- Larger application footprint.

---

## Decision 3: Layered Architecture

### Decision

The application follows a layered architecture.

### Layers

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Reasoning

- Separation of concerns.
- Easier testing and maintenance.
- Improved code organization.
- Better scalability.

### Trade-Offs

Pros:

- Cleaner codebase.
- Easier debugging.
- Better maintainability.

Cons:

- Additional abstraction layers.
- Slight increase in complexity.

---

## Decision 4: MySQL Database

### Decision

MySQL was selected as the primary database.

### Reasoning

- Relational structure suits URL-user relationships.
- Reliable and mature database system.
- Excellent Spring Boot integration.
- Strong indexing capabilities.

### Trade-Offs

Pros:

- Data consistency.
- ACID compliance.
- Good query performance.

Cons:

- Horizontal scaling requires additional planning.

---

## Decision 5: JPA and Hibernate

### Decision

Hibernate with Spring Data JPA was chosen as the persistence layer.

### Reasoning

- Simplifies database operations.
- Reduces boilerplate SQL.
- Supports entity relationships.
- Automatic query generation.

### Trade-Offs

Pros:

- Faster development.
- Object-oriented data access.
- Simplified CRUD operations.

Cons:

- Generated queries may require optimization.
- Learning curve for advanced mappings.

---

## Decision 6: JWT Authentication

### Decision

JWT was selected as the authentication mechanism.

### Reasoning

- Stateless authentication.
- Suitable for REST APIs.
- Eliminates server-side session storage.
- Easy integration with frontend applications.

### Trade-Offs

Pros:

- Scalable.
- Lightweight.
- Works well in distributed environments.

Cons:

- Token revocation is more complex.
- Requires careful secret-key management.

---

## Decision 7: Spring Security

### Decision

Spring Security was selected for authentication and authorization.

### Reasoning

- Industry-standard security framework.
- Integrates seamlessly with Spring Boot.
- Supports JWT and OAuth2.
- Highly customizable.

### Trade-Offs

Pros:

- Robust security.
- Flexible configuration.
- Large community support.

Cons:

- Steeper learning curve.

---

## Decision 8: BCrypt Password Hashing

### Decision

Passwords are stored using BCrypt hashing.

### Reasoning

- One-way hashing algorithm.
- Automatically generates salts.
- Resistant to brute-force attacks.
- Industry-standard password storage mechanism.

### Trade-Offs

Pros:

- Strong security.
- Widely accepted best practice.

Cons:

- Slightly slower authentication due to hashing cost.

---

## Decision 9: Email-Based Authentication

### Decision

Email is used as the primary user identifier.

### Reasoning

- Naturally unique.
- Easier for users to remember.
- Common practice in modern applications.
- Eliminates the need for separate usernames.

### Trade-Offs

Pros:

- Simplified user experience.
- Unique identification.

Cons:

- Email changes require account updates.

---

## Decision 10: Click Count Tracking

### Decision

The system stores a click count for each shortened URL.

### Reasoning

- Provides basic analytics.
- Allows users to measure URL usage.
- Supports future analytics features.

### Trade-Offs

Pros:

- Simple implementation.
- Useful insights.

Cons:

- Limited analytics capabilities compared to dedicated analytics systems.

---

## Decision 11: URL Ownership Model

### Decision

Each shortened URL is associated with a specific user.

### Reasoning

- Enables user-specific URL management.
- Prevents unauthorized access to URL records.
- Supports future dashboard features.

### Trade-Offs

Pros:

- Better security.
- Better user experience.

Cons:

- Additional database relationship management.

---

## Future Design Considerations

The following technologies may be introduced in future versions:

### OAuth2 Authentication

Reason:

- Simplified login experience.
- Social authentication support.

---

### Redis Caching

Reason:

- Faster URL lookup.
- Reduced database load.

---

### Docker Deployment

Reason:

- Simplified deployment.
- Environment consistency.

---

### Analytics Service

Reason:

- Advanced click tracking.
- Device and location insights.

---

### API Gateway

Reason:

- Centralized routing.
- Improved scalability.

---

## Summary

The current design decisions prioritize simplicity, security, maintainability, and scalability.

The chosen architecture and technology stack provide a strong foundation for future enhancements while keeping the implementation suitable for educational, portfolio, and production-oriented use cases.