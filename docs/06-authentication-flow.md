## Introduction

This document describes the authentication and authorization mechanism used in the SnapURL system.

The application currently uses JWT (JSON Web Token) based authentication with Spring Security to secure protected endpoints while maintaining a stateless architecture.

Future versions may include OAuth2 authentication providers such as Google Login.

---

## Authentication Overview

SnapURL uses the following authentication flow:

1. User submits email and password.
2. Credentials are validated by Spring Security.
3. A JWT token is generated upon successful authentication.
4. The client stores the JWT token.
5. The client sends the token in subsequent requests.
6. The JWT filter validates the token.
7. The authenticated user is granted access to protected resources.

---

## Components Involved

### UserService

Responsibilities:

- Load user details
- Authenticate users
- Generate JWT tokens

---

### JWTService

Responsibilities:

- Generate JWT tokens
- Extract user information from tokens
- Validate token authenticity
- Check token expiration

---

### JWTFilter

Responsibilities:

- Intercept incoming requests
- Extract JWT from Authorization header
- Validate token
- Populate Spring Security Context

---

### AppSecurity

Responsibilities:

- Configure security rules
- Define public endpoints
- Protect authenticated resources

---

## Login Flow

### Step 1: User Login Request

```http
POST /login
```

Request:

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

---

### Step 2: Credential Validation

Spring Security validates:

- User existence
- Password correctness

Flow:

```text
Login Request
      |
      v
AuthenticationManager
      |
      v
UserDetailsService
      |
      v
Database Verification
```

---

### Step 3: JWT Generation

After successful authentication:

```text
User Authenticated
        |
        v
JWTService.generateToken()
        |
        v
JWT Returned
```

Example Response:

```json
{
  "message": "Login Successful",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## JWT Structure

A JWT consists of:

```text
Header
Payload
Signature
```

Example:

```text
xxxxx.yyyyy.zzzzz
```

Payload contains:

```json
{
  "sub": "user@example.com",
  "iat": 1710000000,
  "exp": 1710003600
}
```

Where:

- sub = user email
- iat = issued time
- exp = expiration time

---

## Accessing Protected Endpoints

After login, the client must include:

```http
Authorization: Bearer <JWT_TOKEN>
```

Example:

```http
GET /my-urls
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## JWT Validation Flow

```text
Incoming Request
        |
        v
JWT Filter
        |
        v
Extract Token
        |
        v
Extract Email
        |
        v
Validate Token
        |
        v
Load User Details
        |
        v
Create Authentication Object
        |
        v
Security Context Updated
        |
        v
Controller Execution
```

---

## Security Context Population

When a token is valid:

```text
JWT Filter
      |
      v
SecurityContextHolder
      |
      v
Authenticated User Available
```

This allows protected endpoints to identify the currently logged-in user without requiring email or username in the request body.

Example:

```java
Authentication authentication;

String email = authentication.getName();
```

---

## Authorization Flow

Authorization determines what resources a user may access.

### Public Endpoints

Accessible without authentication:

```text
/login
/{shortCode}
```

Examples:

```http
POST /login

GET /abc123
```

---

### Protected Endpoints

Require a valid JWT token:

```text
/my-urls
/shorten
```

Examples:

```http
GET /my-urls

POST /shorten
```

---

## Error Handling

### Invalid Credentials

Response:

```http
401 Unauthorized
```

Reason:

- Incorrect email
- Incorrect password

---

### Missing Token

Response:

```http
401 Unauthorized
```

Reason:

- Authorization header not provided

---

### Expired Token

Response:

```http
401 Unauthorized
```

Reason:

- JWT expiration time exceeded

---

### Invalid Token Signature

Response:

```http
401 Unauthorized
```

Reason:

- Token modified or forged

---

## Password Security

User passwords are stored using:

```text
BCrypt
```

Benefits:

- One-way hashing
- Salted passwords
- Resistant to brute-force attacks

---

## Why JWT?

JWT was selected because:

- Stateless authentication
- Suitable for REST APIs
- No server-side session storage
- Easy integration with frontend applications
- Scalable in distributed environments

---

## Future Authentication Enhancements

The following improvements may be added in future releases:

### OAuth2 Authentication

Providers:

- Google
- GitHub

Flow:

```text
User
   |
   v
OAuth Provider
   |
   v
SnapURL
   |
   v
JWT Generation
```

---

### Refresh Tokens

Benefits:

- Longer user sessions
- Improved security
- Reduced re-authentication frequency

---

### Role-Based Access Control (RBAC)

Potential Roles:

```text
USER
ADMIN
```

Capabilities:

- User management
- URL moderation
- System administration

---

## Summary

The SnapURL authentication system uses JWT and Spring Security to provide secure, stateless authentication and authorization.

The design ensures:

- Secure user authentication
- Protected API access
- User-specific resource ownership
- Scalability for future OAuth2 integration