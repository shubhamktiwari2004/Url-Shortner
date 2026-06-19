## Introduction

This document defines the REST API endpoints exposed by the SnapURL system.

It includes:

- Endpoint definitions
- Request formats
- Response formats
- Authentication requirements
- HTTP status codes

Base URL:

```text
http://localhost:8081
```

---

## Authentication

Protected endpoints require a valid JWT token.

Header Format:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# Authentication APIs

## Login

Authenticate a user and generate a JWT token.

### Endpoint

```http
POST /login
```

### Authentication Required

```text
No
```

### Request

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

### Success Response

```http
200 OK
```

```json
{
  "message": "Login Successful",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Error Responses

```http
401 Unauthorized
```

```json
{
  "message": "Invalid Credentials"
}
```

---

# URL APIs

## Create Short URL

Creates a shortened URL.

### Endpoint

```http
POST /shorten
```

### Authentication Required

```text
Yes
```

### Headers

```http
Authorization: Bearer <JWT_TOKEN>
```

### Request

```json
{
  "originalUrl": "https://www.google.com"
}
```

### Success Response

```http
201 Created
```

```json
{
  "shortCode": "abc123",
  "shortUrl": "http://localhost:8081/abc123"
}
```

### Error Responses

```http
400 Bad Request
```

```json
{
  "message": "Invalid URL"
}
```

---

## Redirect Short URL

Redirects a user to the original URL.

### Endpoint

```http
GET /{shortCode}
```

Example:

```http
GET /abc123
```

### Authentication Required

```text
No
```

### Success Response

```http
302 Found
```

Redirects to:

```text
https://www.google.com
```

### Error Response

```http
404 Not Found
```

```json
{
  "message": "Short URL not found"
}
```

---

## Get User URLs

Returns URLs owned by the authenticated user.

### Endpoint

```http
GET /my-urls
```

### Authentication Required

```text
Yes
```

### Headers

```http
Authorization: Bearer <JWT_TOKEN>
```

### Success Response

```http
200 OK
```

```json
[
  {
    "id": 1,
    "originalUrl": "https://www.google.com",
    "shortCode": "abc123",
    "clickCount": 12
  },
  {
    "id": 2,
    "originalUrl": "https://github.com",
    "shortCode": "xyz789",
    "clickCount": 5
  }
]
```

### Error Response

```http
401 Unauthorized
```

```json
{
  "message": "Unauthorized"
}
```

---

# Common Response Codes

| Status Code | Meaning |
|------------|----------|
| 200 | Success |
| 201 | Resource Created |
| 302 | Redirect |
| 400 | Bad Request |
| 401 | Unauthorized |
| 404 | Resource Not Found |
| 500 | Internal Server Error |

---

# JWT Usage Example

## Login

```http
POST /login
```

Receive:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Access Protected API

```http
GET /my-urls
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# Future APIs

The following endpoints may be introduced in future versions:

### OAuth2 Login

```http
GET /oauth2/authorization/google
```

### URL Analytics

```http
GET /analytics/{shortCode}
```

### Custom Alias

```http
POST /custom-alias
```

### QR Code Generation

```http
GET /qr/{shortCode}
```

---

## Summary

The SnapURL API follows RESTful design principles and provides endpoints for authentication, URL shortening, URL redirection, and user-specific URL management.

Authentication is handled using JWT, ensuring stateless and secure communication between clients and the server.