# Capacity Estimation

## Version

| Property     | Value                             |
| ------------ | --------------------------------- |
| Project      | Warehouse Management System (WMS) |
| Version      | 1.0                               |
| Document     | Capacity Estimation               |
| Architecture | Cloud Native                      |

---

# 1. Overview

Capacity estimation helps determine the infrastructure required to handle the expected workload while maintaining acceptable performance, scalability, and availability.

This document estimates the system size for a medium-to-large warehouse platform and serves as the basis for infrastructure sizing and future scaling decisions.

---

# 2. Assumptions

| Metric                    | Value      |
| ------------------------- | ---------- |
| Warehouses                | 500        |
| Products                  | 10 Million |
| Storage Bins              | 2 Million  |
| Daily Orders              | 1 Million  |
| Daily Active Users        | 100,000    |
| Peak Concurrent Users     | 10,000     |
| Product Images            | 10 Million |
| Availability              | 99.9%      |
| Average API Response Time | < 200 ms   |

---

# 3. User Traffic

## Daily Users

```
100,000
```

---

## Daily Requests

Average Requests per User

```
50
```

Total Requests

```
100,000 × 50

=

5,000,000 Requests / Day
```

---

## Average Requests Per Second

```
5,000,000

/

86,400

≈ 58 RPS
```

---

## Peak Traffic

Peak traffic is generally 5–10 times the average.

Assuming 10x peak:

```
580 Requests / Second
```

Design Target

```
600 RPS
```

---

# 4. API Distribution

| API               | Percentage |
| ----------------- | ---------- |
| Product Search    | 40%        |
| Product Details   | 20%        |
| Inventory Updates | 10%        |
| Orders            | 15%        |
| Warehouse APIs    | 10%        |
| Reports           | 5%         |

---

# Estimated RPS

| API             | Peak RPS |
| --------------- | -------- |
| Search Product  | 240      |
| Product Details | 120      |
| Inventory APIs  | 60       |
| Order APIs      | 90       |
| Warehouse APIs  | 60       |
| Reports         | 30       |

---

# 5. Read vs Write Ratio

Warehouse systems are typically read-heavy.

Read Operations

```
80%
```

Write Operations

```
20%
```

---

Peak Reads

```
480 RPS
```

Peak Writes

```
120 RPS
```

---

# 6. Product Storage Estimation

Average Product Size

```
1 KB
```

Products

```
10 Million
```

Storage

```
10,000,000 × 1 KB

=

10 GB
```

Adding indexes and metadata

Estimated

```
25 GB
```

---

# 7. Warehouse Storage

Warehouse Record

```
1 KB
```

Warehouses

```
500
```

Storage

```
<1 MB
```

---

# 8. Bin Storage

Average Bin Record

```
0.5 KB
```

Bins

```
2 Million
```

Storage

```
1 GB
```

---

# 9. Order Storage

Average Order Size

```
2 KB
```

Daily Orders

```
1 Million
```

Daily Storage

```
2 GB
```

---

Annual Storage

```
730 GB
```

Round Off

```
1 TB
```

---

# 10. Amazon S3 Storage

Average Image Size

```
500 KB
```

Images

```
10 Million
```

Storage

```
5 TB
```

---

Reports

```
200 MB / Day
```

Annual

```
73 GB
```

---

# 11. Network Bandwidth

Average API Response

```
5 KB
```

Peak Requests

```
600 RPS
```

Bandwidth

```
600 × 5 KB

=

3 MB/sec
```

Approximately

```
24 Mbps
```

---

# 12. DynamoDB Capacity

## Peak Reads

```
480 RPS
```

---

## Peak Writes

```
120 RPS
```

---

Use

```
On-Demand Capacity Mode
```

Reasons

- Automatic Scaling
- No Capacity Planning
- Cost Effective
- Handles Traffic Spikes

---

# 13. S3 Requests

Image Uploads

```
100,000 / Day
```

Image Downloads

```
1 Million / Day
```

S3 easily supports this workload.

---

# 14. SNS Capacity

Order Created Events

```
1 Million / Day
```

Low Inventory Events

```
50,000 / Day
```

Report Events

```
500 / Day
```

Total

```
≈1.05 Million Events / Day
```

---

# 15. Lambda Capacity

Daily Report

```
1 Invocation / Day
```

Low Inventory Alerts

```
50,000 Invocations / Day
```

Cleanup Jobs

```
24 Invocations / Day
```

Total

```
≈50,025 Invocations / Day
```

---

# 16. CloudWatch Logs

Average Log Size

```
2 KB
```

Requests

```
5 Million / Day
```

Daily Log Volume

```
10 GB
```

Monthly

```
300 GB
```

Apply log retention policies to control storage costs.

---

# 17. Compute Sizing

Initial Deployment

| Service              | Instances |
| -------------------- | --------- |
| Inventory Service    | 2         |
| Warehouse Service    | 2         |
| Order Service        | 2         |
| Notification Service | 2         |

Each Instance

```
2 vCPU

4 GB RAM
```

---

Future

Horizontal Auto Scaling

```
2

↓

5

↓

10

↓

20
```

---

# 18. Availability Targets

| Metric                | Target                   |
| --------------------- | ------------------------ |
| Uptime                | 99.9%                    |
| API Latency           | <200 ms                  |
| Database Availability | 99.99%                   |
| Object Storage        | 99.999999999% Durability |
| Error Rate            | <1%                      |

---

# 19. Bottlenecks

Possible bottlenecks include:

- Large product searches
- Inventory contention
- High image upload volume
- Burst order traffic
- Excessive logging

---

Mitigation Strategies

- DynamoDB GSIs
- Optimistic Locking
- Auto Scaling
- S3 Multipart Upload
- CloudWatch Monitoring
- Event-Driven Processing

---

# 20. Scaling Strategy

## Horizontal Scaling

```
Inventory Service

2

↓

5

↓

10

↓

20
```

---

## Database Scaling

DynamoDB

- Auto Scaling
- Automatic Partitioning

---

## Object Storage

Amazon S3

Virtually unlimited capacity.

---

## Messaging

Amazon SNS

Automatically scales with message volume.

---

## Lambda

Automatic scaling without infrastructure management.

---

# 21. Cost Optimization

To reduce operational costs:

- Use DynamoDB On-Demand initially.
- Configure S3 lifecycle policies for old reports.
- Archive logs using CloudWatch retention.
- Compress report files before uploading.
- Use Lambda only for event-driven tasks.
- Scale services based on CPU and request metrics.

---

# 22. Capacity Summary

| Component        | Estimated Size     |
| ---------------- | ------------------ |
| Products         | 10 Million         |
| Warehouses       | 500                |
| Bins             | 2 Million          |
| Orders / Year    | 365 Million        |
| Product Storage  | 25 GB              |
| Order Storage    | 1 TB               |
| Image Storage    | 5 TB               |
| Daily Traffic    | 5 Million Requests |
| Peak Throughput  | 600 RPS            |
| Peak Reads       | 480 RPS            |
| Peak Writes      | 120 RPS            |
| Daily Events     | 1.05 Million       |
| Daily Log Volume | 10 GB              |

---

# 23. Conclusion

The Warehouse Management System is designed to support:

- Millions of products
- Hundreds of warehouses
- Hundreds of millions of orders
- Terabytes of storage
- Hundreds of requests per second
- Event-driven processing
- Automatic scaling using AWS managed services

By leveraging DynamoDB, Amazon S3, SNS, Lambda, CloudWatch, and Infrastructure as Code with AWS CDK, the platform can grow horizontally without significant architectural changes. The sizing assumptions provide a practical baseline for development and can be refined as real production traffic patterns emerge.