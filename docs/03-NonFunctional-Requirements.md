# Non-Functional Requirements

## Version

| Property      | Value                             |
| ------------- | --------------------------------- |
| Project       | Warehouse Management System (WMS) |
| Version       | 1.0                               |
| Document Type | Non-Functional Requirements (NFR) |
| Author        | Sanjay Kumar                      |
| Status        | Draft                             |

---

# 1. Purpose

This document defines the non-functional requirements of the Warehouse Management System (WMS). These requirements specify how the system should perform rather than what functionality it provides.

The system must be highly available, scalable, secure, reliable, observable, and maintainable while supporting enterprise-level warehouse operations.

---

# 2. Availability

## Requirement

The application should remain available even during infrastructure failures.

### Target

| Requirement        | Value           |
| ------------------ | --------------- |
| Availability       | 99.9%           |
| Planned Downtime   | < 2 hours/month |
| Unplanned Downtime | Minimal         |

---

## Strategy

- Deploy across multiple Availability Zones
- Health checks
- Auto Restart
- API Gateway
- CloudWatch Alarms
- Retry Mechanism

---

# 3. Scalability

The application should support business growth without architecture changes.

---

## Expected Scale

| Component        | Target      |
| ---------------- | ----------- |
| Warehouses       | 500+        |
| Products         | 10 Million  |
| Orders           | 500,000/day |
| Images           | 20 Million  |
| Concurrent Users | 10,000      |
| Requests/sec     | 5,000+      |

---

## Scalability Strategy

Horizontal Scaling

Stateless Services

AWS Lambda

DynamoDB Auto Scaling

S3 Unlimited Storage

SNS Event Driven Processing

---

# 4. Performance

## API Response Time

| API             | Expected Response |
| --------------- | ----------------- |
| Login           | <300 ms           |
| Product Search  | <100 ms           |
| Product Details | <150 ms           |
| Create Product  | <500 ms           |
| Upload Image    | <2 sec            |
| Place Order     | <800 ms           |

---

## Database Performance

Read latency

<20 ms

Write latency

<30 ms

---

## Cache Lookup

<5 ms

---

# 5. Reliability

The system must continue functioning despite failures.

---

## Requirements

No duplicate orders

No inventory inconsistency

Automatic retry

Graceful degradation

---

## Reliability Strategy

Idempotent APIs

Optimistic Locking

Retry Pattern

SNS Retry

Dead Letter Queue (Future)

---

# 6. Consistency

Inventory data must remain consistent.

---

## Rules

Stock cannot become negative.

One reservation per inventory unit.

Inventory updates must be atomic.

---

## Strategy

Optimistic Locking

Conditional Update in DynamoDB

Transactions where required

---

# 7. Security

The application must protect user data and AWS resources.

---

## Authentication

JWT Authentication

---

## Authorization

Role-Based Access Control (RBAC)

---

## Encryption

HTTPS

TLS 1.2+

Server-side Encryption for S3

---

## IAM

Least Privilege Principle

Separate IAM Roles

No Hardcoded Credentials

---

## Secrets

AWS Secrets Manager (Future)

Environment Variables

---

# 8. Fault Tolerance

The system should recover automatically from failures.

---

## Failure Cases

Lambda Failure

SNS Failure

Network Failure

Database Timeout

Image Upload Failure

---

## Recovery Strategy

Retry

Circuit Breaker

Fallback

CloudWatch Alarm

---

# 9. Maintainability

The application should be easy to maintain.

---

## Standards

Clean Code

SOLID Principles

Layered Architecture

Microservices

Dependency Injection

---

## Code Quality

Sonar Rules

Code Reviews

Unit Testing

Documentation

---

# 10. Extensibility

New features should be added with minimal changes.

---

Future Modules

Supplier Service

Shipping Service

Payment Service

Analytics Service

Mobile Application

---

# 11. Usability

REST APIs

Swagger Documentation

Meaningful Error Messages

Consistent API Responses

---

# 12. Observability

The application must provide complete visibility.

---

## Logging

Application Logs

API Logs

Error Logs

AWS SDK Logs

---

## Monitoring

CloudWatch Metrics

CloudWatch Dashboards

CloudWatch Alarms

---

## Metrics

CPU

Memory

Request Count

Error Count

Latency

Throughput

Inventory Updates

Orders Created

---

# 13. Auditability

Every business operation should be traceable.

---

Audit Information

Created By

Updated By

Timestamp

Request ID

Operation

---

# 14. Backup & Recovery

## Database

DynamoDB Point-in-Time Recovery

---

## Images

Amazon S3 Versioning

Cross-Region Replication (Future)

---

## Reports

Stored in Amazon S3

---

# 15. Disaster Recovery

Recovery should be possible after infrastructure failures.

---

## Strategy

Infrastructure as Code (AWS CDK)

Automated Deployment

Data Backup

CloudFormation Recovery

---

# 16. Compliance

The system should follow enterprise standards.

---

Secure APIs

Encrypted Communication

Access Control

Audit Logs

Least Privilege

---

# 17. Deployment Requirements

Deploy using

AWS CDK

GitHub Actions

Docker

---

Environment

Development

Testing

Production

---

# 18. Logging Requirements

Every request must log

Request ID

API Name

Execution Time

Status

Exception

User

---

Example

```

2026-07-20T10:30:22Z

POST /products

Status : SUCCESS

Duration : 145 ms

RequestId : 123456

```

---

# 19. Monitoring Requirements

CloudWatch Dashboard should display

API Requests

API Errors

Lambda Executions

SNS Messages

DynamoDB Read Capacity

DynamoDB Write Capacity

S3 Upload Count

CPU Usage

Memory Usage

---

# 20. Capacity Planning

## Estimated Daily Load

| Component      | Volume    |
| -------------- | --------- |
| Product Reads  | 2 Million |
| Product Writes | 300,000   |
| Orders         | 500,000   |
| Image Uploads  | 100,000   |
| Notifications  | 1 Million |
| Reports        | 500       |

---

Estimated Storage

| Resource        | Size         |
| --------------- | ------------ |
| DynamoDB        | 500 GB       |
| Amazon S3       | 5 TB         |
| CloudWatch Logs | 100 GB/month |

---

# 21. Service Level Objectives (SLO)

| Metric           | Target  |
| ---------------- | ------- |
| Availability     | 99.9%   |
| API Success Rate | 99.5%   |
| Error Rate       | <0.5%   |
| Product Search   | <100 ms |
| Order Creation   | <800 ms |
| Image Upload     | <2 sec  |

---

# 22. Non-Functional Architecture

```

                     Client

                        │

                        ▼

                 API Gateway

                        │

──────────────────────────────────────────────

          Spring Boot Microservices

──────────────────────────────────────────────

Availability

↓

Horizontal Scaling

↓

Load Distribution

↓

CloudWatch Monitoring

↓

AWS SDK

↓

DynamoDB

↓

Amazon S3

↓

Amazon SNS

↓

Lambda

──────────────────────────────────────────────

```

---

# 23. Summary

The Warehouse Management System is designed to meet enterprise-grade non-functional requirements by providing:

- High Availability (99.9%)
- Horizontal Scalability
- Secure Authentication and Authorization
- Reliable Inventory Management
- Event-Driven Communication
- Comprehensive Monitoring
- Automated Recovery
- Infrastructure as Code
- Cloud-Native Deployment
- Production-Ready Architecture

These characteristics ensure that the application can support large-scale warehouse operations while remaining secure, performant, maintainable, and fault tolerant.
