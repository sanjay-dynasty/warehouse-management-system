# Warehouse Management System (WMS)

> A cloud-native Warehouse Management System (WMS) built using **Java, Spring Boot, AWS SDK, AWS CDK, Lambda, DynamoDB, S3, API Gateway, SNS, CloudWatch, JUnit, and Mockito**. The system manages warehouse operations, inventory, product storage, and order fulfillment using a scalable microservices architecture.

---

# Project Overview

Warehouse Management System (WMS) is designed to simulate a real-world Amazon-style warehouse backend. It provides capabilities for inventory management, warehouse operations, product image management, order processing, notifications, and reporting.

The project focuses on cloud-native development using AWS services while following software engineering best practices, including High-Level Design (HLD), Low-Level Design (LLD), Clean Architecture, SOLID principles, Design Patterns, Infrastructure as Code (AWS CDK), automated testing, and observability.

---

# Features

## Inventory Management
- Create Product
- Update Product
- Delete Product
- Search Product
- View Product Details
- Manage Product Inventory

## Warehouse Management
- Create Warehouse
- Manage Warehouse Capacity
- Create Storage Bins
- Allocate Products to Bins
- Locate Products inside Warehouse

## Inventory Allocation
- Reserve Inventory
- Release Inventory
- Stock Transfer
- Low Inventory Detection

## Order Management
- Create Order
- Cancel Order
- Track Order
- Warehouse Allocation
- Inventory Reservation

## Product Image Management
- Upload Product Images
- Store Images in Amazon S3
- Retrieve Image URLs

## Notification System
- Order Created
- Order Cancelled
- Low Stock Alert
- Daily Report Notification

## Reporting
- Daily Inventory Report
- Warehouse Utilization Report
- Low Stock Report
- Top Selling Products

---

# Tech Stack

## Backend

- Java 21
- Spring Boot 3
- Maven
- REST APIs

## AWS Services

- AWS CDK (TypeScript)
- API Gateway
- AWS Lambda
- DynamoDB
- Amazon S3
- Amazon SNS
- Amazon CloudWatch
- IAM

## Testing

- JUnit 5
- Mockito

## Documentation

- Swagger / OpenAPI

## DevOps

- Docker
- GitHub Actions
- AWS Deployment

---

# System Architecture

```
                        Client

                           │

                    API Gateway

                           │

──────────────────────────────────────────────────────────

                Spring Boot Microservices

──────────────────────────────────────────────────────────

Inventory Service

Warehouse Service

Order Service

Notification Service

Report Service

──────────────────────────────────────────────────────────

                    AWS SDK (Java)

──────────────────────────────────────────────────────────

DynamoDB

Amazon S3

Amazon SNS

AWS Lambda

CloudWatch

IAM

──────────────────────────────────────────────────────────
```

---

# AWS Services Used

| Service     | Purpose                              |
| ----------- | ------------------------------------ |
| API Gateway | REST API Entry Point                 |
| DynamoDB    | Product, Warehouse & Order Storage   |
| Amazon S3   | Product Images                       |
| Lambda      | Report Generation & Inventory Alerts |
| SNS         | Event Driven Communication           |
| CloudWatch  | Monitoring & Logging                 |
| IAM         | Security                             |
| AWS CDK     | Infrastructure as Code               |

---

# Microservices

## Inventory Service

Responsibilities

- Product CRUD
- Inventory Management
- Stock Updates

---

## Warehouse Service

Responsibilities

- Warehouse Management
- Storage Bin Management
- Product Allocation

---

## Order Service

Responsibilities

- Create Orders
- Cancel Orders
- Reserve Inventory
- Warehouse Allocation

---

## Notification Service

Responsibilities

- Consume SNS Events
- Send Notifications
- Log Events

---

## Report Service

Responsibilities

- Generate Daily Reports
- Low Inventory Reports
- Upload Reports to S3

---

# Database Design

## Product

- productId
- name
- description
- category
- price
- stock
- warehouseId
- imageUrl

---

## Warehouse

- warehouseId
- warehouseName
- city
- state
- capacity
- availableCapacity

---

## Bin

- binId
- warehouseId
- aisle
- rack
- level
- productId

---

## Order

- orderId
- customerId
- warehouseId
- status
- totalAmount
- createdAt

---

# Project Structure

```
warehouse-management-system
│
├── docs
│
├── diagrams
│
├── infrastructure
│   └── aws-cdk
│
├── services
│   ├── inventory-service
│   ├── warehouse-service
│   ├── order-service
│   ├── notification-service
│   ├── report-service
│   └── common-library
│
├── docker
│
├── scripts
│
├── README.md
│
└── pom.xml
```

---

# Project Phases

| Phase    | Description                                         |
| -------- | --------------------------------------------------- |
| Phase 0  | Requirement Analysis & High Level Design            |
| Phase 1  | Low Level Design                                    |
| Phase 2  | Project Setup                                       |
| Phase 3  | AWS CDK Infrastructure                              |
| Phase 4  | Inventory Service                                   |
| Phase 5  | Product Image Service                               |
| Phase 6  | Warehouse Service                                   |
| Phase 7  | Inventory Allocation Engine                         |
| Phase 8  | LLD Components (LRU Cache, Bin Cache, Rate Limiter) |
| Phase 9  | Order Service                                       |
| Phase 10 | SNS Integration                                     |
| Phase 11 | Lambda Functions                                    |
| Phase 12 | API Gateway                                         |
| Phase 13 | CloudWatch Monitoring                               |
| Phase 14 | Security                                            |
| Phase 15 | Unit Testing                                        |
| Phase 16 | Docker                                              |
| Phase 17 | CI/CD Pipeline                                      |
| Phase 18 | AWS Deployment                                      |
| Phase 19 | System Design Review                                |
| Phase 20 | Interview Readiness                                 |

---

# System Design Concepts

- High Level Design (HLD)
- Low Level Design (LLD)
- SOLID Principles
- Clean Architecture
- Design Patterns
- Event Driven Architecture
- Repository Pattern
- Builder Pattern
- Strategy Pattern
- Observer Pattern
- Factory Pattern
- API Gateway Pattern
- Caching
- Rate Limiting
- Idempotency
- Horizontal Scaling
- Infrastructure as Code

---

# AWS SDK Integrations

- Amazon S3
- DynamoDB Enhanced Client
- SNS Client
- CloudWatch Client
- Lambda Client

---

# Testing Strategy

- Unit Testing
- Integration Testing
- Mockito-based AWS SDK Mocking
- Service Layer Testing
- Controller Testing
- Repository Testing
- Exception Testing

---

# Learning Outcomes

By completing this project, you will gain hands-on experience in:

- Java Backend Development
- Spring Boot Microservices
- AWS SDK
- AWS CDK
- DynamoDB
- Amazon S3
- AWS Lambda
- Amazon SNS
- API Gateway
- CloudWatch
- Infrastructure as Code
- System Design (HLD & LLD)
- Event Driven Architecture
- JUnit & Mockito
- Docker
- CI/CD
- AWS Deployment

---

# Future Enhancements

- Redis Caching
- SQS Integration
- Dead Letter Queue (DLQ)
- OpenSearch Integration
- Multi-Region Deployment
- Auto Scaling
- Prometheus & Grafana Monitoring
- Distributed Tracing
- Authentication & Authorization
- Role-Based Access Control (RBAC)

---

# License

This project is developed for learning, system design practice, and cloud-native backend development.