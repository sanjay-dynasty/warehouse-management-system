# Low Level Design (LLD)

## Version

| Property | Value                             |
| -------- | --------------------------------- |
| Project  | Warehouse Management System (WMS) |
| Version  | 1.0                               |
| Document | Low Level Design                  |

---

# 1. Overview

This document defines the internal design of each microservice.

It covers:

- Package structure
- Layers
- Class responsibilities
- Design patterns
- Entity relationships
- Exception handling
- Validation
- Logging
- Testing strategy

---

# 2. Microservices

- Inventory Service
- Warehouse Service
- Order Service
- Notification Service
- Report Service

---

# 3. Standard Project Structure

```

inventory-service

├── controller
├── service
│ ├── impl
├── repository
├── entity
├── dto
├── mapper
├── exception
├── config
├── util
├── validator
└── constants

```

---

# 4. Layer Responsibilities

### Controller

- Accept HTTP requests
- Validate request
- Return ResponseEntity

---

### Service

- Business logic
- Transactions
- AWS SDK calls

---

### Repository

- DynamoDB interaction

---

### DTO

- Request
- Response

---

### Mapper

- DTO ↔ Entity conversion

---

### Validator

- Business validations

---

### Exception

- Global exception handling

---

# 5. Inventory Service

## Responsibilities

- Create Product
- Update Product
- Delete Product
- Upload Image
- Inventory Management

---

## Main Classes

```

ProductController

↓

ProductService

↓

ProductServiceImpl

↓

ProductRepository

↓

DynamoDB

```

---

# 6. Warehouse Service

```

WarehouseController

↓

WarehouseService

↓

WarehouseServiceImpl

↓

WarehouseRepository

↓

DynamoDB

```

Responsibilities

- Create Warehouse
- Manage Bins
- Allocate Products

---

# 7. Order Service

```

OrderController

↓

OrderService

↓

InventoryClient

↓

Product Validation

↓

Reserve Inventory

↓

OrderRepository

↓

SNS Publisher

```

Responsibilities

- Create Order
- Cancel Order
- Publish Events

---

# 8. Notification Service

Responsibilities

- Consume SNS Events
- Send Notifications
- Log Events

---

# 9. Report Service

Responsibilities

- Generate Reports
- Upload Reports to S3
- Trigger Lambda

---

# 10. DTO Design

Example

CreateProductRequest

```
name
description
category
price
stock
warehouseId
```

CreateProductResponse

```
productId
message
```

---

# 11. Entity Design

Product

```
productId
name
description
category
price
stock
warehouseId
imageUrl
createdAt
updatedAt
```

---

# 12. Exception Handling

Global Exception Handler

Handles

- ValidationException
- ProductNotFoundException
- WarehouseNotFoundException
- InventoryException
- AwsException
- RuntimeException

Returns

```
{
  "success": false,
  "message": "...",
  "timestamp": "..."
}
```

---

# 13. Validation

Product

- Name Required
- Price > 0
- Stock >= 0

Warehouse

- Capacity > 0

Order

- Quantity > 0

---

# 14. Logging

Log

- Request
- Response
- Exception
- Execution Time

Using

- SLF4J
- Logback
- CloudWatch

---

# 15. Design Patterns

- Dependency Injection
- Builder Pattern
- Factory Pattern
- Strategy Pattern
- Singleton (Spring Beans)
- Repository Pattern

---

# 16. SOLID Principles

- Single Responsibility
- Open/Closed
- Liskov Substitution
- Interface Segregation
- Dependency Inversion

---

# 17. Testing

Unit Testing

- JUnit 5
- Mockito

Integration Testing

- Spring Boot Test

Target Coverage

- >80%

---

# 18. Future LLD Components

These will be implemented during development:

- Inventory Allocation Engine
- Warehouse Selection Strategy
- LRU Cache
- Rate Limiter
- Retry Handler
- Idempotency Handler

---

# 19. Summary

The application follows Clean Architecture with layered Spring Boot services, SOLID principles, Repository pattern, DTO mapping, centralized exception handling, and comprehensive unit testing to ensure maintainability and scalability.