## Introduction

This document describes the low-level design of the SnapURL system. It details the internal components, their responsibilities, and interactions within the application.

The purpose of this document is to provide a blueprint for implementation and maintenance of the system.

---

## Package Structure

```text
com.snapurl
│
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── exception
├── util
└── config
```

---

## Entity Design

### User

Represents an authenticated user of the system.

#### Attributes

| Field | Type | Description |
|---------|---------|---------|
| id | Long | Unique user identifier |
| name | String | User name |
| email | String | Unique email address |
| password | String | BCrypt hashed password |

#### Responsibilities

- Store user information
- Support authentication
- Own multiple URL mappings

---

### UrlMapping

Represents a shortened URL.

#### Attributes

| Field | Type | Description |
|---------|---------|---------|
| id | Long | Unique identifier |
| originalUrl | String | Original URL |
| shortCode | String | Generated short code |
| clickCount | Long | Number of visits |
| createdAt | LocalDateTime | Creation timestamp |
| user | User | URL owner |

#### Responsibilities

- Store URL mappings
- Track clicks
- Associate URLs with users

---

## Repository Design

### UserRepository

#### Responsibilities

- Find users by email
- Persist user data
- Support authentication

#### Example Methods

```java
Optional<User> findByEmail(String email);
```

---

### UrlRepository

#### Responsibilities

- Manage URL mappings
- Retrieve URLs using short codes
- Retrieve URLs belonging to a user

#### Example Methods

```java
Optional<UrlMapping> findByShortCode(String shortCode);

List<UrlMapping> findByUser(User user);
```

---

## Service Design

### UserService

#### Responsibilities

- Load user details
- Authenticate users
- Generate JWT tokens
- Manage user-related operations

#### Major Methods

```java
loginUser()

verify()

loadUserByUsername()
```

---

### UrlService

#### Responsibilities

- Generate short codes
- Create URL mappings
- Handle redirection
- Track click counts

#### Major Methods

```java
createShortUrl()

getOriginalUrl()

getUserUrls()
```

---

### JWTService

#### Responsibilities

- Generate JWT tokens
- Validate JWT tokens
- Extract user email from token

#### Major Methods

```java
generateToken()

validateToken()

extractUsername()
```

---

## Controller Design

### AuthController

#### Responsibilities

- Login users
- Return JWT token
- Return user information

#### Endpoints

```http
POST /login
```

---

### UrlController

#### Responsibilities

- Create short URLs
- Redirect URLs
- Retrieve user URLs

#### Endpoints

```http
POST /shorten

GET /{shortCode}

GET /my-urls
```

---

## Security Components

### AppSecurity

#### Responsibilities

- Configure Spring Security
- Define public and protected routes
- Register authentication provider

---

### JWTFilter

#### Responsibilities

- Read Authorization header
- Extract JWT token
- Validate token
- Set authentication context

---

## Exception Handling

### GlobalExceptionHandler

#### Responsibilities

- Handle application exceptions
- Return meaningful error responses
- Standardize API error handling

Example:

```java
@ExceptionHandler(Exception.class)
```

---

## Database Relationships

```text
User (1)
   |
   |
   ▼
UrlMapping (N)
```

One user can own multiple shortened URLs.

Each URL belongs to exactly one user.

---

## Request Lifecycle

### URL Creation

```text
Client
   |
Controller
   |
UrlService
   |
Repository
   |
Database
```

---

### Authenticated Request

```text
Client
   |
JWT Filter
   |
Security Context
   |
Controller
   |
Service
   |
Repository
```

---

## Design Considerations

- Separation of concerns
- Layered architecture
- Stateless authentication
- Reusable business logic
- Database abstraction through repositories
- Easy future integration of OAuth2 and Redis