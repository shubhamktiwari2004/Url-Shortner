## Introduction

This document describes the database design of the SnapURL system. The database is responsible for storing user information, URL mappings, authentication-related data, and URL analytics.

The system uses MySQL as its primary relational database management system.

---

## Database Overview

Database Name:

```text
snapurl
```

The database consists of the following core entities:

- User
- UrlMapping

---

## Entity Relationship Diagram (ERD)

```text
+------------------+
|      User        |
+------------------+
| id (PK)          |
| name             |
| email            |
| password         |
+---------+--------+
          |
          | 1
          |
          | N
+---------v--------+
|    UrlMapping    |
+------------------+
| id (PK)          |
| original_url     |
| short_code       |
| click_count      |
| created_at       |
| user_id (FK)     |
+------------------+
```

---

## User Table

### Purpose

Stores user account information used for authentication and URL ownership.

### Table Structure

| Column | Type | Constraints |
|----------|----------|----------|
| id | BIGINT | Primary Key |
| name | VARCHAR(255) | Not Null |
| email | VARCHAR(255) | Unique, Not Null |
| password | VARCHAR(255) | Not Null |

### Example Entity

```java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;
}
```

---

## UrlMapping Table

### Purpose

Stores shortened URLs and their corresponding original URLs.

### Table Structure

| Column | Type | Constraints |
|----------|----------|----------|
| id | BIGINT | Primary Key |
| original_url | TEXT | Not Null |
| short_code | VARCHAR(50) | Unique, Not Null |
| click_count | BIGINT | Default 0 |
| created_at | TIMESTAMP | Not Null |
| user_id | BIGINT | Foreign Key |

### Example Entity

```java
@Entity
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;

    private String shortCode;

    private Long clickCount;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
```

---

## Relationship Design

### User → UrlMapping

Relationship Type:

```text
One-To-Many
```

Meaning:

- One user can create multiple shortened URLs.
- Each shortened URL belongs to exactly one user.

JPA Mapping:

```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

---

## Constraints

### User Table

#### Email Uniqueness

```sql
UNIQUE(email)
```

Purpose:

- Prevent duplicate user accounts.
- Ensure unique user identification.

---

### UrlMapping Table

#### Short Code Uniqueness

```sql
UNIQUE(short_code)
```

Purpose:

- Ensure every short URL maps to a single destination.

---

## Indexing Strategy

### Email Index

```sql
INDEX(email)
```

Reason:

- Frequently used during authentication.

---

### Short Code Index

```sql
INDEX(short_code)
```

Reason:

- Used for URL lookup and redirection.
- Critical for application performance.

---

## Query Patterns

### Find User By Email

```sql
SELECT *
FROM users
WHERE email = ?;
```

Used During:

- Login
- Authentication
- JWT Validation

---

### Find URL By Short Code

```sql
SELECT *
FROM url_mapping
WHERE short_code = ?;
```

Used During:

- URL Redirection

---

### Find URLs Owned By User

```sql
SELECT *
FROM url_mapping
WHERE user_id = ?;
```

Used During:

- My URLs functionality

---

## Data Integrity Rules

The system enforces the following rules:

- Every URL mapping must belong to a valid user.
- Every email address must be unique.
- Every short code must be unique.
- Click count cannot be negative.
- URLs cannot exist without an original URL.

---

## Future Database Enhancements

Future versions of SnapURL may introduce:

### Analytics Table

```text
UrlAnalytics
```

For:

- Device tracking
- Browser tracking
- Geographic analytics

---

### Refresh Token Table

```text
RefreshToken
```

For:

- OAuth2 Authentication
- Token refresh mechanisms

---

### Custom Domain Table

```text
CustomDomain
```

For:

- User-specific branded URLs

---

## Summary

The database design follows a normalized relational structure that ensures:

- Data consistency
- Referential integrity
- Efficient querying
- Scalability for future enhancements

The current design supports URL shortening, user authentication, URL ownership, and click tracking while remaining flexible for future system growth.