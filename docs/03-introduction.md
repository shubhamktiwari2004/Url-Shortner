## Introduction

This document describes the high-level architecture of the SnapURL system. It outlines the major components, their responsibilities, and the flow of requests through the application.

The purpose of this document is to provide a clear understanding of the system structure before diving into detailed implementation and class-level design.

---

## System Overview

SnapURL is a URL shortening platform that allows users to:

- Create shortened URLs
- Redirect users to original URLs
- Track URL click counts
- Manage user-specific URLs
- Secure protected operations using JWT Authentication

The application follows a layered architecture consisting of presentation, business, persistence, and security layers.

---

## High-Level Architecture

```text
+-------------------+
|      Client       |
| (Browser/Postman) |
+---------+---------+
          |
          v
+-------------------+
|  Spring Security  |
|    JWT Filter     |
+---------+---------+
          |
          v
+-------------------+
|    Controllers    |
+---------+---------+
          |
          v
+-------------------+
|     Services      |
+---------+---------+
          |
          v
+-------------------+
|   Repositories    |
+---------+---------+
          |
          v
+-------------------+
|      MySQL        |
+-------------------+