# AWS Architecture

## Version

| Property       | Value                             |
| -------------- | --------------------------------- |
| Project        | Warehouse Management System (WMS) |
| Version        | 1.0                               |
| Cloud Provider | Amazon Web Services               |
| Infrastructure | AWS CDK                           |
| Architecture   | Cloud Native                      |

---

# 1. Overview

The Warehouse Management System is built as a cloud-native application using AWS managed services.

Infrastructure is provisioned using AWS CDK, ensuring repeatable, version-controlled deployments.

The architecture follows an event-driven, serverless-friendly design while maintaining stateless microservices.

---

# 2. AWS Services Used

| Service     | Purpose                  |
| ----------- | ------------------------ |
| API Gateway | API Entry Point          |
| Lambda      | Report Generation        |
| DynamoDB    | Product & Order Storage  |
| Amazon S3   | Product Images & Reports |
| Amazon SNS  | Event Notifications      |
| CloudWatch  | Logs, Metrics & Alarms   |
| IAM         | Security                 |
| AWS CDK     | Infrastructure as Code   |

---

# 3. AWS Infrastructure Diagram

```

                    Internet

                        │

                        ▼

                Amazon API Gateway

                        │

────────────────────────────────────────────

Spring Boot Services

Inventory Service

Warehouse Service

Order Service

Notification Service

Report Service

────────────────────────────────────────────

              AWS SDK (Java)

────────────────────────────────────────────

         DynamoDB

         Amazon S3

         Amazon SNS

         CloudWatch

         Lambda

────────────────────────────────────────────

```

---

# 4. Infrastructure Provisioned using AWS CDK

The following resources are created using AWS CDK.

## Networking

API Gateway

---

## Compute

Lambda Function

---

## Database

DynamoDB

Product Table

Warehouse Table

Bin Table

Order Table

---

## Storage

Amazon S3 Bucket

Product Images

Reports

---

## Messaging

Amazon SNS Topic

---

## Monitoring

CloudWatch Log Groups

CloudWatch Dashboard

CloudWatch Alarms

---

## Security

IAM Roles

IAM Policies

---

# 5. API Gateway

Purpose

Acts as the single entry point for all client requests.

Responsibilities

- Request Routing
- Authentication
- Authorization
- Rate Limiting
- Logging

Routes

POST /products

GET /products/{id}

POST /orders

POST /warehouses

GET /reports

---

# 6. DynamoDB

Purpose

Primary NoSQL database.

Tables

## Product Table

Partition Key

productId

---

## Warehouse Table

Partition Key

warehouseId

---

## Bin Table

Partition Key

binId

---

## Order Table

Partition Key

orderId

---

Advantages

- Serverless
- High Availability
- Auto Scaling
- Low Latency

---

# 7. Amazon S3

Purpose

Object Storage

Stores

- Product Images
- CSV Reports
- PDF Reports

Bucket Structure

```

wms-storage

|

|-- products/

|

|-- reports/

|

|-- temp/

```

---

# 8. Amazon SNS

Purpose

Event-driven communication.

Published Events

OrderCreated

OrderCancelled

InventoryLow

ReportGenerated

Subscribers

Notification Service

Future Analytics Service

Audit Service

---

# 9. AWS Lambda

Purpose

Background Processing

Functions

GenerateDailyReport

LowInventoryAlert

CleanupTempFiles

Trigger

CloudWatch Scheduler

SNS

---

# 10. CloudWatch

Purpose

Application Monitoring

Stores

Application Logs

Lambda Logs

API Logs

Metrics

Dashboards

Alarms

---

Dashboard Metrics

Request Count

API Latency

Error Rate

SNS Messages

Lambda Executions

CPU

Memory

---

# 11. IAM

Purpose

Secure AWS Resources

IAM Roles

Inventory Service

- DynamoDB
- S3

Warehouse Service

- DynamoDB

Order Service

- DynamoDB
- SNS

Notification Service

- SNS

Report Service

- Lambda
- S3

---

# 12. AWS SDK Usage

Inventory Service

↓

DynamoDB

Amazon S3

--------------------------

Order Service

↓

SNS

DynamoDB

--------------------------

Report Service

↓

Lambda

Amazon S3

--------------------------

Notification Service

↓

SNS

CloudWatch

---

# 13. CloudWatch Alarm Strategy

Create alarms for

High API Errors

Lambda Failures

SNS Publish Failure

High DynamoDB Latency

High Memory Usage

High CPU Usage

---

# 14. AWS Request Flow

## Product Creation

```

Client

↓

API Gateway

↓

Inventory Service

↓

AWS SDK

↓

DynamoDB

↓

CloudWatch

↓

Response

```

---

## Product Image Upload

```

Client

↓

API Gateway

↓

Inventory Service

↓

AWS SDK

↓

Amazon S3

↓

Save Image URL

↓

DynamoDB

```

---

## Order Creation

```

Client

↓

API Gateway

↓

Order Service

↓

Inventory Service

↓

Reserve Stock

↓

DynamoDB

↓

SNS

↓

Notification Service

↓

CloudWatch

```

---

## Daily Report

```

CloudWatch Scheduler

↓

Lambda

↓

DynamoDB

↓

Generate CSV

↓

Amazon S3

↓

SNS

↓

Notification

```

---

# 15. Infrastructure Deployment

Deployment Steps

AWS CDK

↓

CloudFormation

↓

Create Resources

↓

Deploy Lambda

↓

Deploy API Gateway

↓

Create DynamoDB

↓

Create S3

↓

Create SNS

↓

Ready

---

# 16. Future AWS Enhancements

Amazon SQS

Dead Letter Queue

OpenSearch

Redis (Elasticache)

Secrets Manager

EventBridge

AWS X-Ray

AWS WAF

AWS Cognito

CloudFront

---

# 17. Benefits

- Fully Managed Infrastructure
- Infrastructure as Code
- High Availability
- Horizontal Scalability
- Event-Driven Architecture
- Secure
- Cost Effective
- Cloud Native
- Production Ready