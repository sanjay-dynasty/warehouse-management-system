# High Level Design (HLD)

## Version

| Property           | Value                             |
| ------------------ | --------------------------------- |
| Project            | Warehouse Management System (WMS) |
| Version            | 1.0                               |
| Document Type      | High Level Design                 |
| Architecture Style | Microservices                     |
| Cloud Provider     | AWS                               |
| Author             | Sanjay Kumar                      |

---

# Table of Contents

1. System Overview
2. Design Goals
3. Architecture Principles
4. High Level Architecture
5. Service Decomposition
6. Component Diagram
7. Client Request Flow
8. Technology Stack
9. Service Responsibilities

---

# 1. System Overview

Warehouse Management System (WMS) is a cloud-native microservices application responsible for managing warehouses, inventory, product storage, order fulfillment, notifications, and reporting.

The system is designed to handle millions of products and thousands of concurrent users while maintaining low latency, high availability, and scalability.

The application follows an event-driven architecture using AWS services and Infrastructure as Code (AWS CDK).

---

# 2. Design Goals

The architecture has been designed with the following objectives.

## Functional Goals

- Inventory Management
- Warehouse Management
- Product Storage
- Product Image Management
- Order Processing
- Event Notification
- Report Generation

---

## Non Functional Goals

- High Availability
- Horizontal Scalability
- Low Latency
- Fault Tolerance
- Security
- Observability
- Cloud Native
- Easy Deployment
- Easy Maintenance

---

# 3. Architecture Principles

The application follows the following principles.

## 3.1 Microservices

Each business capability is implemented as an independent service.

Example

Inventory Service does not contain Order logic.

Warehouse Service does not contain Notification logic.

Each service owns its own responsibility.

---

## 3.2 Single Responsibility Principle

Each service performs only one business responsibility.

Example

Inventory Service

Responsible only for

- Product
- Inventory

Nothing else.

---

## 3.3 Loose Coupling

Services communicate through REST APIs and SNS events.

No service directly accesses another service's database.

---

## 3.4 High Cohesion

Related business logic remains inside the same service.

Example

Everything related to inventory stays inside Inventory Service.

---

## 3.5 Event Driven Architecture

Business events are published through Amazon SNS.

Examples

Order Created

Inventory Updated

Low Stock

Order Cancelled

Notification Service consumes these events independently.

---

## 3.6 Stateless Services

Spring Boot services remain stateless.

No session is stored inside the application.

JWT is used for authentication.

This allows horizontal scaling.

---

## 3.7 Infrastructure as Code

Every AWS resource is created using AWS CDK.

No manual AWS Console configuration.

---

# 4. High Level Architecture

```

                           Client

                              │

                    HTTPS REST APIs

                              │

                              ▼

                     Amazon API Gateway

                              │

────────────────────────────────────────────────────────────────

                    Spring Boot Microservices

────────────────────────────────────────────────────────────────

Inventory Service

Warehouse Service

Order Service

Notification Service

Report Service

────────────────────────────────────────────────────────────────

                      AWS SDK (Java)

────────────────────────────────────────────────────────────────

DynamoDB

Amazon S3

Amazon SNS

AWS Lambda

CloudWatch

IAM

────────────────────────────────────────────────────────────────

```

---

# 5. Service Decomposition

Instead of building one large monolithic application, the system is divided into multiple independent services.

```

Warehouse Management System

│

├── Inventory Service

├── Warehouse Service

├── Order Service

├── Notification Service

└── Report Service

```

Each service owns its own business logic.

---

# 6. Why Microservices?

### Inventory changes frequently.

### Orders require different scaling.

### Reports are scheduled jobs.

### Notifications are asynchronous.

Keeping them separate improves

- scalability
- deployment
- maintainability
- fault isolation

---

# 7. Component Diagram

```

                    +---------------------+
                    |     Client App      |
                    +----------+----------+
                               |
                               |
                               ▼
                    +---------------------+
                    |    API Gateway      |
                    +----------+----------+
                               |
        -------------------------------------------------
        |             |              |                  |
        ▼             ▼              ▼                  ▼

+----------------+ +----------------+ +----------------+ +----------------------+
| Inventory      | | Warehouse      | | Order          | | Notification         |
| Service        | | Service        | | Service        | | Service              |
+--------+-------+ +--------+-------+ +--------+-------+ +-----------+----------+
         |                  |                  |                     |
         |                  |                  |                     |
         ▼                  ▼                  ▼                     ▼

+----------------+ +----------------+ +----------------+ +----------------------+
| DynamoDB       | | DynamoDB       | | DynamoDB       | | Amazon SNS           |
+----------------+ +----------------+ +----------------+ +----------------------+

         |
         |
         ▼

+----------------------+

Amazon S3

+----------------------+

         |

         ▼

CloudWatch

```

---

# 8. Request Lifecycle

Example

Customer creates an order.

```

Client

↓

API Gateway

↓

Order Service

↓

Validate Request

↓

Inventory Service

↓

Reserve Stock

↓

Save Order

↓

Publish SNS Event

↓

Notification Service

↓

CloudWatch Logs

↓

HTTP Response

```

---

# 9. Technology Stack

## Backend

Java 21

Spring Boot

Spring MVC

Spring Validation

Maven

REST APIs

---

## Cloud

AWS CDK

AWS SDK

Lambda

API Gateway

SNS

CloudWatch

IAM

Amazon S3

DynamoDB

---

## Testing

JUnit 5

Mockito

---

## DevOps

Docker

GitHub Actions

---

# 10. Service Responsibilities

---

## Inventory Service

Responsible for

- Product CRUD
- Inventory
- Product Search
- Stock Updates
- Product Validation

Owns

Product Table

---

## Warehouse Service

Responsible for

- Warehouse CRUD
- Storage Bins
- Product Allocation
- Bin Location
- Capacity Management

Owns

Warehouse Table

Bin Table

---

## Order Service

Responsible for

- Create Order
- Cancel Order
- Reserve Inventory
- Warehouse Allocation

Owns

Order Table

---

## Notification Service

Responsible for

- Receive SNS Events
- Send Notifications
- Log Notifications

Owns

No database

Consumes SNS

---

## Report Service

Responsible for

- Daily Reports
- Warehouse Reports
- Inventory Reports
- Export Reports

Uses

AWS Lambda

Amazon S3

---

# 11. Why These AWS Services?

| AWS Service | Reason                                      |
| ----------- | ------------------------------------------- |
| API Gateway | Secure entry point for all APIs             |
| DynamoDB    | High throughput, low latency NoSQL database |
| Amazon S3   | Durable and scalable object storage         |
| SNS         | Publish-subscribe event communication       |
| Lambda      | Serverless scheduled report generation      |
| CloudWatch  | Monitoring, logging, and alerting           |
| IAM         | Secure access control                       |
| AWS CDK     | Infrastructure as Code                      |

---

# 12. High Level Benefits

The selected architecture provides:

- Independent deployments
- Better fault isolation
- Horizontal scalability
- Event-driven communication
- Cloud-native deployment
- Easy monitoring
- Loose coupling
- High availability
- Enterprise-ready design
- Amazon-style backend architecture

# 13. Service Communication

The Warehouse Management System follows a hybrid communication model.

- Synchronous communication using REST APIs.
- Asynchronous communication using Amazon SNS.

This combination provides low latency for user-facing operations while enabling loose coupling for background processing.

---

## Communication Types

| Communication         | Technology | Use Case                 |
| --------------------- | ---------- | ------------------------ |
| Client → API Gateway  | HTTPS      | API Requests             |
| API Gateway → Service | REST       | Request Routing          |
| Service → Service     | REST       | Immediate Business Logic |
| Service → SNS         | Publish    | Event Notification       |
| SNS → Subscriber      | Push       | Async Processing         |
| Service → AWS         | AWS SDK    | Cloud Resource Access    |

---

# 14. Service Interaction Diagram

```

                          Client

                             │

                             ▼

                     Amazon API Gateway

                             │

──────────────────────────────────────────────────────────────

        Inventory Service

        Warehouse Service

        Order Service

        Notification Service

        Report Service

──────────────────────────────────────────────────────────────

Inventory Service
        │
        ▼
 DynamoDB

Inventory Service
        │
        ▼
 Amazon S3

Order Service
        │
        ▼
 Amazon SNS

SNS
        │
        ▼
 Notification Service

Report Service
        │
        ▼
 AWS Lambda

Lambda
        │
        ▼
 Amazon S3

All Services
        │
        ▼
 CloudWatch

```

---

# 15. Database Ownership

Each microservice owns its own data.

No service directly accesses another service's database.

```

Inventory Service

↓

Product Table

Inventory Table

----------------------------

Warehouse Service

↓

Warehouse Table

Bin Table

----------------------------

Order Service

↓

Order Table

----------------------------

Notification Service

↓

No Database

Consumes SNS

----------------------------

Report Service

↓

Reads Required Data

Stores Reports in S3

```

---

# Why Database Per Service?

Advantages

- Loose coupling
- Independent deployment
- Better scalability
- Better fault isolation
- Easier maintenance

---

# 16. API Gateway Routing

All client requests first reach API Gateway.

```

POST /products

↓

Inventory Service

----------------------------

POST /warehouses

↓

Warehouse Service

----------------------------

POST /orders

↓

Order Service

----------------------------

GET /reports

↓

Report Service

```

Benefits

- Single Entry Point
- Authentication
- Authorization
- Rate Limiting
- Logging
- Monitoring

---

# 17. Request Flow — Create Product

```

Client

↓

POST /products

↓

API Gateway

↓

Inventory Service

↓

Validate Request

↓

Save Product

↓

DynamoDB

↓

Return Response

↓

CloudWatch Logs

```

---

# 18. Request Flow — Upload Product Image

```

Client

↓

POST /products/{id}/image

↓

API Gateway

↓

Inventory Service

↓

Validate Image

↓

AWS SDK

↓

Amazon S3

↓

Receive Image URL

↓

Update Product

↓

DynamoDB

↓

Response

```

---

# 19. Request Flow — Create Warehouse

```

Client

↓

POST /warehouses

↓

API Gateway

↓

Warehouse Service

↓

Validate Request

↓

Save Warehouse

↓

DynamoDB

↓

Response

```

---

# 20. Request Flow — Allocate Product to Bin

```

Warehouse Employee

↓

Scan Product

↓

Warehouse Service

↓

Find Empty Bin

↓

Assign Product

↓

Update Bin Table

↓

Update Product Table

↓

Response

```

---

# 21. Request Flow — Create Order

```

Customer

↓

POST /orders

↓

API Gateway

↓

Order Service

↓

Validate Order

↓

Inventory Service

↓

Check Stock

↓

Reserve Stock

↓

Save Order

↓

Publish Event

↓

Amazon SNS

↓

Response

```

---

# 22. SNS Event Flow

Whenever a business event occurs

```

Order Created

↓

SNS Topic

↓

Notification Service

↓

CloudWatch

↓

Email

```

Another example

```

Inventory Below Threshold

↓

SNS

↓

Notification Service

↓

Warehouse Manager

```

---

# 23. Lambda Flow

Every midnight

```

CloudWatch Scheduler

↓

AWS Lambda

↓

Read Inventory

↓

Generate CSV

↓

Upload Report

↓

Amazon S3

↓

SNS Notification

```

---

# 24. CloudWatch Logging Flow

```

API Request

↓

Spring Boot

↓

Business Logic

↓

Log Request

↓

CloudWatch

↓

Dashboard

↓

Alarm

```

---

# 25. AWS SDK Usage

Every service communicates with AWS using AWS SDK for Java.

```

Inventory Service

↓

AWS SDK

↓

DynamoDB

```

```

Inventory Service

↓

AWS SDK

↓

Amazon S3

```

```

Order Service

↓

AWS SDK

↓

Amazon SNS

```

```

Report Service

↓

AWS SDK

↓

Lambda

```

---

# 26. Synchronous Communication

Used when immediate response is required.

Examples

```

Order Service

↓

Inventory Service

↓

Check Inventory

↓

Return Response

```

```

Inventory Service

↓

Warehouse Service

↓

Find Warehouse

↓

Return Response

```

Characteristics

- Immediate response
- REST APIs
- Request-Response
- Low latency

---

# 27. Asynchronous Communication

Used when immediate response is not required.

```

Order Created

↓

SNS

↓

Notification Service

```

```

Inventory Updated

↓

SNS

↓

Analytics (Future)

```

Characteristics

- Event Driven
- Loose Coupling
- Better Scalability
- Independent Processing

---

# 28. Design Decisions

---

## Why DynamoDB?

Reasons

- Serverless
- Low Latency
- Horizontal Scaling
- No Infrastructure Management
- High Availability

Suitable For

- Product
- Inventory
- Orders
- Warehouses

---

## Why Amazon S3?

Reasons

- Unlimited Storage
- Durable
- Low Cost
- Versioning
- Easy Image Hosting

Suitable For

- Product Images
- Reports

---

## Why SNS?

Reasons

- Publish-Subscribe
- Loose Coupling
- Event Driven
- Retry Support
- Multiple Subscribers

Suitable For

- Notifications
- Analytics
- Audit Logs

---

## Why Lambda?

Reasons

- Serverless
- Pay Per Execution
- Scheduled Jobs
- Auto Scaling

Suitable For

- Reports
- Inventory Alerts
- Scheduled Cleanup

---

## Why API Gateway?

Reasons

- Authentication
- Authorization
- Rate Limiting
- Request Routing
- Monitoring

---

## Why AWS CDK?

Reasons

- Infrastructure as Code
- Version Control
- Repeatable Deployments
- Easier Maintenance
- Automated Provisioning

---

# 29. Request Processing Lifecycle

```

Client

↓

API Gateway

↓

Authentication

↓

Controller

↓

Validation

↓

Service

↓

Business Logic

↓

Repository

↓

AWS SDK

↓

DynamoDB

↓

SNS

↓

CloudWatch

↓

Response

```

---

# 30. Benefits of This Architecture

- Cloud Native
- Event Driven
- Horizontally Scalable
- Highly Available
- Fault Tolerant
- Easy to Maintain
- Easy to Deploy
- Production Ready
- Enterprise Grade
- Amazon Style Architecture

# 31. Deployment Architecture

The Warehouse Management System is deployed entirely on AWS using Infrastructure as Code (AWS CDK).

Every infrastructure component is provisioned automatically through CDK, ensuring consistency across Development, QA, and Production environments.

---

## Deployment Diagram

```

                           Internet
                               │
                               ▼
                     Amazon API Gateway
                               │
                               ▼
                    Spring Boot Services
                               │
        ┌──────────────┬──────────────┬──────────────┐
        │              │              │              │
        ▼              ▼              ▼              ▼
 Inventory       Warehouse        Order      Notification
  Service          Service        Service        Service
        │              │              │
        └───────┬──────┴───────┬──────┘
                │              │
                ▼              ▼
          Amazon DynamoDB   Amazon SNS
                │              │
                │              ▼
                │      Notification Service
                │
                ▼
            Amazon S3
                │
                ▼
         AWS Lambda (Reports)
                │
                ▼
          Amazon S3 Reports

Every Service
      │
      ▼
CloudWatch Logs & Metrics

```

---

# 32. Infrastructure Components

## Compute

- Spring Boot Applications
- AWS Lambda

---

## Networking

- Amazon API Gateway

---

## Storage

- DynamoDB
- Amazon S3

---

## Messaging

- Amazon SNS

---

## Monitoring

- Amazon CloudWatch

---

## Security

- IAM
- JWT Authentication

---

## Infrastructure

- AWS CDK

---

# 33. Security Architecture

```

                 Client

                    │

             JWT Authentication

                    │

                    ▼

              API Gateway

                    │

            JWT Validation

                    │

                    ▼

          Spring Boot Services

                    │

             IAM Credentials

                    │

                    ▼

              AWS Services

```

---

## Security Layers

### Layer 1

HTTPS

All requests must use HTTPS.

---

### Layer 2

JWT Authentication

Every request contains

Authorization

Bearer Token

---

### Layer 3

Role Based Access Control

Roles

- Admin
- Warehouse Manager
- Inventory Manager
- Employee

---

### Layer 4

IAM

Every service receives only the permissions it needs.

Example

Inventory Service

Can access

- DynamoDB
- S3

Cannot access

Lambda.

---

### Layer 5

Input Validation

Every API validates

- Required fields
- Invalid values
- File size
- Request format

---

# 34. High Availability

The application is designed for high availability.

```

           API Gateway

                 │

        Load Distribution

                 │

      Multiple Spring Boot Instances

                 │

         DynamoDB Multi-AZ

                 │

           Amazon S3

                 │

          SNS + Lambda

```

---

## High Availability Features

API Gateway

No Single Point of Failure

DynamoDB

Managed High Availability

S3

11 9's Durability

Lambda

Automatic Scaling

SNS

Managed Messaging

---

# 35. Scalability Strategy

The application supports horizontal scaling.

```

            100 Users

                │

                ▼

          Inventory Service

                │

       Horizontal Scaling

                │

      5 Service Instances

                │

         Same Database

```

---

## Scaling Strategy

Inventory Service

Horizontal

Warehouse Service

Horizontal

Order Service

Horizontal

Notification Service

Horizontal

Lambda

Automatic

S3

Unlimited

DynamoDB

Auto Scaling

---

# 36. Caching Strategy

## Current Version

In-memory cache

Implemented using

LinkedHashMap

LRU

TTL

Example

```

Warehouse Bin

↓

Bin Cache

↓

Memory

↓

Database

```

---

## Future

Redis

Distributed Cache

Product Cache

Warehouse Cache

Inventory Cache

---

# 37. Failure Handling

## Product Image Upload Failure

```

Upload Image

↓

S3 Failure

↓

Retry

↓

Log

↓

Return Error

```

---

## DynamoDB Failure

```

Save Product

↓

Timeout

↓

Retry

↓

CloudWatch

↓

Failure Response

```

---

## SNS Failure

```

Publish Event

↓

Retry

↓

CloudWatch Alarm

```

---

## Lambda Failure

```

Generate Report

↓

Lambda Error

↓

Retry

↓

CloudWatch Alarm

```

---

# 38. Retry Strategy

Retry only for transient failures.

Retry Count

3

Backoff Strategy

Exponential Backoff

Retryable Operations

- S3 Upload
- DynamoDB Write
- SNS Publish

Not Retryable

Validation Errors

Authentication Errors

Bad Requests

---

# 39. Monitoring Architecture

```

Application

      │

Application Logs

      │

CloudWatch Logs

      │

CloudWatch Metrics

      │

Dashboard

      │

CloudWatch Alarm

```

---

## Metrics

API Requests

API Errors

Latency

Memory

CPU

Inventory Updates

Orders

SNS Events

Lambda Executions

---

# 40. Logging Strategy

Every request generates logs.

Example

```

Timestamp

Request Id

User

API

Execution Time

Status

Exception

```

---

Example

```

Request Id : 7846523

API : POST /products

Status : SUCCESS

Time : 145 ms

```

---

# 41. Disaster Recovery

## DynamoDB

Point-in-Time Recovery

---

## S3

Versioning

---

## Infrastructure

AWS CDK

Entire infrastructure can be recreated.

---

# 42. Technology Decision Matrix

| Requirement    | Technology      | Reason                 |
| -------------- | --------------- | ---------------------- |
| REST APIs      | Spring Boot     | Enterprise standard    |
| Cloud          | AWS             | Managed Services       |
| Database       | DynamoDB        | Scalable NoSQL         |
| Images         | Amazon S3       | Object Storage         |
| Events         | Amazon SNS      | Event Driven           |
| Monitoring     | CloudWatch      | Native AWS Monitoring  |
| Reports        | Lambda          | Serverless             |
| Infrastructure | AWS CDK         | Infrastructure as Code |
| Testing        | JUnit & Mockito | Unit Testing           |
| Build          | Maven           | Dependency Management  |
| Documentation  | Swagger         | API Documentation      |

---

# 43. Future Enhancements

- Amazon SQS
- Dead Letter Queue (DLQ)
- Redis Cache
- OpenSearch
- Prometheus
- Grafana
- Distributed Tracing
- Kubernetes (EKS)
- EventBridge
- Multi-Region Deployment
- Auto Scaling Groups
- AWS Secrets Manager

---

# 44. Architecture Advantages

### Scalability

Independent services scale independently.

---

### Availability

Managed AWS services provide high availability.

---

### Performance

Low latency using DynamoDB and S3.

---

### Reliability

Retry mechanisms and monitoring reduce failures.

---

### Security

JWT

IAM

HTTPS

Input Validation

---

### Maintainability

Clean Architecture

SOLID Principles

Microservices

---

### Extensibility

New services can be added without impacting existing services.

---

### Observability

CloudWatch provides complete monitoring and logging.

---

# 45. End-to-End Request Lifecycle

```

                      Client
                         │
                         ▼
                 Amazon API Gateway
                         │
               JWT Authentication
                         │
                         ▼
               Spring Boot Controller
                         │
                  Request Validation
                         │
                         ▼
                   Service Layer
                         │
                  Business Logic
                         │
                    Repository
                         │
                     AWS SDK
                         │
      ┌──────────┬──────────┬───────────┐
      │          │          │           │
      ▼          ▼          ▼           ▼
 DynamoDB     Amazon S3    SNS      Lambda
      │          │          │           │
      └──────────┴──────────┴───────────┘
                         │
                         ▼
                  CloudWatch Logs
                         │
                         ▼
                  HTTP Response

```

---

# 46. Conclusion

The Warehouse Management System (WMS) is designed as a cloud-native, event-driven microservices application following enterprise architecture principles.

Key characteristics include:

- Microservices Architecture
- Event-Driven Communication
- Infrastructure as Code (AWS CDK)
- AWS SDK Integration
- Serverless Computing with Lambda
- Scalable NoSQL Storage using DynamoDB
- Object Storage using Amazon S3
- Monitoring with CloudWatch
- Secure API Gateway
- Stateless Services
- High Availability
- Horizontal Scalability
- Production-Ready Design

This architecture closely resembles modern enterprise warehouse and fulfillment systems used by organizations such as Amazon and provides an excellent foundation for learning system design, AWS cloud services, and backend engineering best practices.