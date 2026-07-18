# Request Flows

## Version

| Property     | Value                             |
| ------------ | --------------------------------- |
| Project      | Warehouse Management System (WMS) |
| Version      | 1.0                               |
| Document     | Request Flow Design               |
| Architecture | Microservices                     |

---

# 1. Overview

This document describes how requests travel through the system.

Each request flow illustrates:

- Request Origin
- API Gateway Routing
- Service Interaction
- AWS SDK Calls
- Database Operations
- Event Publishing
- Logging
- Response

---

# 2. Create Product Flow

## Objective

Create a new product inside a warehouse.

```
Warehouse Manager

        │

        ▼

POST /products

        │

        ▼

API Gateway

        │

        ▼

Inventory Service

        │

Validate Request

        │

Generate Product ID

        │

Check Warehouse Exists

        │

Save Product

        │

AWS SDK

        │

        ▼

DynamoDB

        │

        ▼

CloudWatch Logs

        │

        ▼

HTTP 201 Created
```

---

# Sequence Diagram

```
Client

 │

 │ POST /products

 ▼

API Gateway

 │

 ▼

Inventory Service

 │

 │ Validate

 │

 ▼

DynamoDB

 │

 │ Save Product

 ▼

CloudWatch

 │

 ▼

Response
```

---

# 3. Upload Product Image

## Objective

Upload image to Amazon S3.

```
Warehouse Manager

       │

       ▼

POST /products/{id}/image

       │

       ▼

API Gateway

       │

       ▼

Inventory Service

       │

Validate File

       │

AWS SDK

       │

       ▼

Amazon S3

       │

Receive Image URL

       │

Update Product

       │

DynamoDB

       │

CloudWatch

       │

Response
```

---

# Sequence Diagram

```
Client

 │

 ▼

Inventory Service

 │

 ▼

Amazon S3

 │

 ▼

DynamoDB

 │

 ▼

Response
```

---

# 4. Create Warehouse

```
Admin

 │

 ▼

POST /warehouses

 │

 ▼

API Gateway

 │

 ▼

Warehouse Service

 │

Validate

 │

Save Warehouse

 │

DynamoDB

 │

CloudWatch

 │

Response
```

---

# 5. Create Storage Bin

```
Warehouse Manager

 │

 ▼

POST /warehouses/{id}/bins

 │

 ▼

Warehouse Service

 │

Validate

 │

Generate Bin ID

 │

Save Bin

 │

DynamoDB

 │

Response
```

---

# 6. Allocate Product to Bin

```
Warehouse Employee

 │

 ▼

PUT /bins/{binId}/allocate

 │

 ▼

Warehouse Service

 │

Check Bin

 │

Check Product

 │

Update Bin

 │

Update Product

 │

DynamoDB

 │

CloudWatch

 │

Response
```

---

# 7. Search Product

```
User

 │

 ▼

GET /products

 │

 ▼

API Gateway

 │

 ▼

Inventory Service

 │

Search Product

 │

DynamoDB GSI

 │

Return Products

 │

CloudWatch

 │

Response
```

---

# 8. Reserve Inventory

## Objective

Reserve stock before order creation.

```
Order Service

 │

 ▼

Reserve Inventory

 │

Check Product

 │

Check Stock

 │

Stock Available?

 │

 ├──────────────No──────────────┐
 │                              │
 ▼                              ▼

Update Stock              Return Error

 │

 ▼

DynamoDB

 │

 ▼

Success
```

---

# 9. Create Order Flow

This is the most important business flow.

```
Customer

 │

 ▼

POST /orders

 │

 ▼

API Gateway

 │

 ▼

Order Service

 │

Validate Request

 │

 ▼

Inventory Service

 │

Check Inventory

 │

Reserve Inventory

 │

Update Stock

 │

Return Success

 │

 ▼

Order Service

 │

Save Order

 │

DynamoDB

 │

Publish Event

 │

Amazon SNS

 │

CloudWatch

 │

HTTP 201 Created
```

---

# Detailed Sequence

```
Customer

   │

   ▼

Order Service

   │

   ▼

Inventory Service

   │

Reserve Stock

   │

DynamoDB

   │

Return

   │

Order Service

   │

Save Order

   │

SNS Publish

   │

Return Response
```

---

# 10. SNS Event Flow

```
Order Created

 │

 ▼

Amazon SNS

 │

 ├──────────────┐

 ▼              ▼

Notification    Future Analytics

Service         Service

 │

 ▼

CloudWatch

 │

 ▼

Email
```

---

# 11. Low Inventory Flow

```
Inventory Updated

 │

 ▼

Stock < 10 ?

 │

 ├──────No─────────┐

 │                 │

 ▼                 ▼

Continue      Publish Event

                  │

                  ▼

              Amazon SNS

                  │

                  ▼

         Notification Service

                  │

                  ▼

         Warehouse Manager
```

---

# 12. Daily Report Flow

```
CloudWatch Scheduler

 │

 ▼

AWS Lambda

 │

Read Inventory

 │

DynamoDB

 │

Generate CSV

 │

Amazon S3

 │

Publish SNS Event

 │

Notification Service

 │

CloudWatch
```

---

# 13. Product Lookup Flow

```
User

 │

 ▼

GET /products/{id}

 │

 ▼

API Gateway

 │

 ▼

Inventory Service

 │

Query DynamoDB

 │

Return Product

 │

CloudWatch

 │

Response
```

---

# 14. Warehouse Lookup

```
GET /warehouses/{id}

 │

 ▼

Warehouse Service

 │

DynamoDB

 │

Response
```

---

# 15. Error Flow

```
Client

 │

 ▼

API Gateway

 │

 ▼

Service

 │

Validation Failed

 │

Throw Exception

 │

Global Exception Handler

 │

CloudWatch

 │

HTTP 400
```

---

# 16. Authentication Flow

```
Client

 │

 ▼

JWT Token

 │

 ▼

API Gateway

 │

Validate JWT

 │

Success ?

 │

 ├────────────No────────────┐

 │                          │

 ▼                          ▼

401 Unauthorized       Forward Request

                             │

                             ▼

                     Spring Boot Service
```

---

# 17. CloudWatch Logging Flow

```
Request Received

 │

 ▼

Generate Request ID

 │

 ▼

Execute Business Logic

 │

 ▼

Execution Time

 │

 ▼

CloudWatch Logs

 │

 ▼

Dashboard
```

---

# 18. End-to-End Request Lifecycle

```
Client

   │

   ▼

API Gateway

   │

Authentication

   │

Controller

   │

Validation

   │

Service

   │

Repository

   │

AWS SDK

   │

DynamoDB

   │

SNS

   │

CloudWatch

   │

Response
```

---

# 19. Request Flow Summary

| Flow              | AWS Services      |
| ----------------- | ----------------- |
| Create Product    | DynamoDB          |
| Upload Image      | S3 + DynamoDB     |
| Create Warehouse  | DynamoDB          |
| Create Bin        | DynamoDB          |
| Search Product    | DynamoDB GSI      |
| Create Order      | DynamoDB + SNS    |
| Reserve Inventory | DynamoDB          |
| Low Inventory     | SNS               |
| Daily Report      | Lambda + S3 + SNS |
| Monitoring        | CloudWatch        |

---

# 20. Conclusion

Every request in the Warehouse Management System follows a consistent lifecycle:

1. Client sends request.
2. API Gateway routes the request.
3. Service validates input.
4. Business logic executes.
5. AWS SDK communicates with AWS services.
6. CloudWatch records logs and metrics.
7. SNS publishes business events when required.
8. Response is returned to the client.

This standardized request flow improves maintainability, observability, scalability, and fault isolation across the platform.