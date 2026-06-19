## Introduction

This document defines the functional and non-functional requirements for the SnapURL system. These requirements establish the expected behavior, performance, security, and scalability characteristics of the application.

The requirements outlined in this document serve as the foundation for system design, implementation, testing, and future enhancements.

---

## Functional Requirements

### URL Creation

- The system shall allow users to submit a valid long URL.
- The system shall generate a unique short code for each URL.
- The system shall store the original URL and generated short code in the database.
- The system shall return a shortened URL to the user.

### URL Redirection

- The system shall redirect users to the original URL when a valid short code is accessed.
- The system shall validate the existence of the short code before redirection.
- The system shall return an appropriate error response if the short code does not exist.

### Click Tracking

- The system shall maintain a click count for each shortened URL.
- The system shall increment the click count on every successful redirection.
- The system shall allow retrieval of click statistics for a URL.

### User Authentication

- The system shall allow users to authenticate using email and password.
- The system shall generate a JWT token upon successful authentication.
- The system shall validate JWT tokens for protected endpoints.

### User URL Management

- The system shall associate URLs with authenticated users.
- The system shall allow users to view URLs created by them.
- The system shall prevent users from accessing URLs belonging to other users.

### Error Handling

- The system shall provide meaningful error messages.
- The system shall return appropriate HTTP status codes.
- The system shall handle invalid URLs gracefully.
- The system shall handle authentication and authorization failures securely.

---

## Non-Functional Requirements

### Performance

- URL redirection should be completed with minimal latency.
- Database queries should be optimized for efficient retrieval.
- The application should support concurrent user requests.

### Security

- User passwords shall be stored using BCrypt hashing.
- JWT shall be used for stateless authentication.
- Protected APIs shall require valid authentication tokens.
- Sensitive information shall not be exposed in API responses.

### Scalability

- The system architecture should support future scaling.
- The application should be designed to support caching mechanisms such as Redis.
- The URL generation mechanism should support a growing number of records.

### Maintainability

- The project shall follow a layered architecture.
- Business logic shall be separated from presentation and persistence layers.
- The codebase shall remain modular and extensible.

### Reliability

- URL mappings shall persist reliably in the database.
- Authentication and authorization mechanisms shall function consistently.
- System failures shall not result in data corruption.

---

## Assumptions

- Users provide valid URLs for shortening.
- Users possess valid email addresses for authentication.
- A MySQL database is available and accessible.
- The application is deployed in a stable network environment.

---

## Constraints

- The initial implementation uses a single database instance.
- Authentication is currently based on JWT.
- Analytics are limited to click-count tracking.
- URL shortening is dependent on the uniqueness of generated short codes.

---

## Future Requirements

- OAuth2 Authentication (Google Login)
- Redis Caching
- Advanced Analytics Dashboard
- QR Code Generation
- Custom URL Aliases
- URL Expiration Support
- Docker Deployment
- Rate Limiting
- Admin Dashboard