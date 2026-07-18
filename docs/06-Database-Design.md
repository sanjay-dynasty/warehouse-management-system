# Database Design

## Version

| Property      | Value                             |
| ------------- | --------------------------------- |
| Project       | Warehouse Management System (WMS) |
| Version       | 1.0                               |
| Database      | Amazon DynamoDB                   |
| Document Type | Database Design Document          |
| Author        | Sanjay Kumar                      |

---

# 1. Overview

Warehouse Management System (WMS) uses **Amazon DynamoDB** as its primary database.

Unlike relational databases, DynamoDB is a **NoSQL Key-Value and Document database** optimized for:

- High throughput
- Low latency
- Horizontal scaling
- High availability
- Automatic partitioning
- Serverless operations

Since DynamoDB is designed around **access patterns**, the data model is created based on how the application queries data rather than on normalization.

---

# 2. Why DynamoDB?

## Advantages

- Serverless
- Single-digit millisecond latency
- Auto Scaling
- Multi-AZ replication
- No infrastructure management
- High write throughput
- Seamless AWS SDK integration

---

## Why not MySQL?

Although warehouse data is relational, our workload is **read/write intensive** with predictable access patterns.

Typical operations are:

- Get Product by ID
- Search Product
- Get Warehouse
- Reserve Inventory
- Create Order

These operations benefit from DynamoDB's low latency.

---

# 3. Database Design Principles

The following principles are used.

- One table per business domain
- Every table has a unique Partition Key
- Frequently searched fields use GSI
- Avoid table scans
- Query using indexes whenever possible
- Store images in S3, not DynamoDB

---

# 4. Tables

The system consists of four primary tables.

```

Warehouse Management System

│

├── Product Table

├── Warehouse Table

├── Bin Table

└── Order Table

```

---

# 5. Product Table

## Purpose

Stores product information.

---

## Primary Key

Partition Key

```
productId
```

---

## Attributes

| Attribute   | Type   |
| ----------- | ------ |
| productId   | String |
| name        | String |
| description | String |
| category    | String |
| price       | Number |
| stock       | Number |
| warehouseId | String |
| imageUrl    | String |
| createdAt   | String |
| updatedAt   | String |

---

## Sample Item

```json
{
  "productId":"P1001",
  "name":"Laptop",
  "description":"Dell Latitude",
  "category":"Electronics",
  "price":65000,
  "stock":45,
  "warehouseId":"WH001",
  "imageUrl":"https://s3.amazonaws.com/...",
  "createdAt":"2026-07-20T10:00:00Z"
}
```

---

# 6. Warehouse Table

## Purpose

Stores warehouse information.

---

## Primary Key

```
warehouseId
```

---

## Attributes

| Attribute         | Type   |
| ----------------- | ------ |
| warehouseId       | String |
| warehouseName     | String |
| city              | String |
| state             | String |
| capacity          | Number |
| availableCapacity | Number |
| createdAt         | String |

---

## Sample Item

```json
{
  "warehouseId":"WH001",
  "warehouseName":"Lucknow Warehouse",
  "city":"Lucknow",
  "state":"UP",
  "capacity":100000,
  "availableCapacity":42000
}
```

---

# 7. Bin Table

## Purpose

Stores storage bin information.

Each warehouse contains multiple bins.

---

## Primary Key

```
binId
```

---

## Attributes

| Attribute   | Type    |
| ----------- | ------- |
| binId       | String  |
| warehouseId | String  |
| aisle       | String  |
| rack        | String  |
| shelf       | String  |
| level       | String  |
| productId   | String  |
| occupied    | Boolean |

---

## Sample Item

```json
{
  "binId":"BIN101",
  "warehouseId":"WH001",
  "aisle":"A",
  "rack":"R12",
  "shelf":"S03",
  "level":"L2",
  "productId":"P1001",
  "occupied":true
}
```

---

# 8. Order Table

## Purpose

Stores customer orders.

---

## Primary Key

```
orderId
```

---

## Attributes

| Attribute   | Type   |
| ----------- | ------ |
| orderId     | String |
| customerId  | String |
| warehouseId | String |
| productId   | String |
| quantity    | Number |
| amount      | Number |
| status      | String |
| createdAt   | String |

---

## Sample Item

```json
{
  "orderId":"ORD1001",
  "customerId":"C100",
  "warehouseId":"WH001",
  "productId":"P1001",
  "quantity":2,
  "amount":130000,
  "status":"CREATED"
}
```

---

# 9. Entity Relationship

Although DynamoDB is NoSQL, the logical relationship is:

```

Warehouse

│

├──────────────┐

│              │

▼              ▼

Product      Bin

│

▼

Order

```

---

# 10. Global Secondary Indexes (GSI)

## Product Table

### GSI 1

```
CategoryIndex

Partition Key

category
```

Used for

Search by Category

---

### GSI 2

```
WarehouseIndex

Partition Key

warehouseId
```

Used for

Products in Warehouse

---

### GSI 3

```
ProductNameIndex

Partition Key

name
```

Used for

Search by Name

---

# Warehouse Table

### GSI

```
CityIndex

Partition Key

city
```

Used for

Search Warehouses by City

---

# Order Table

### GSI

```
CustomerIndex

Partition Key

customerId
```

Used for

Customer Order History

---

# 11. Access Patterns

Designing DynamoDB starts with understanding **how the application reads data**.

| Access Pattern             | Table     | Key / Index      |
| -------------------------- | --------- | ---------------- |
| Get Product by ID          | Product   | PK               |
| Search Product by Name     | Product   | ProductNameIndex |
| Search Product by Category | Product   | CategoryIndex    |
| Get Products by Warehouse  | Product   | WarehouseIndex   |
| Get Warehouse by ID        | Warehouse | PK               |
| Get Warehouses by City     | Warehouse | CityIndex        |
| Get Bin by ID              | Bin       | PK               |
| Get Customer Orders        | Order     | CustomerIndex    |
| Get Order by ID            | Order     | PK               |

---

# 12. Read & Write Operations

## Product

Create

Read

Update

Delete

---

## Warehouse

Create

Read

Update

Delete

---

## Inventory

Read

Update

---

## Orders

Create

Read

Cancel

---

# 13. Capacity Planning

Estimated Data

| Entity     | Count       |
| ---------- | ----------- |
| Products   | 10 Million  |
| Warehouses | 500         |
| Bins       | 2 Million   |
| Orders     | 100 Million |

---

Estimated Storage

| Table     | Storage |
| --------- | ------- |
| Product   | 250 GB  |
| Warehouse | 2 GB    |
| Bin       | 50 GB   |
| Order     | 400 GB  |

---

# 14. Partition Strategy

DynamoDB automatically partitions data.

Partition Keys

```
Product

productId

Warehouse

warehouseId

Bin

binId

Order

orderId

```

Random UUIDs ensure even distribution.

---

# 15. Optimistic Locking

Inventory updates require optimistic locking.

Example

```

Current Stock = 10

↓

Reserve 2

↓

Condition

Stock >= 2

↓

Update

Stock = 8

```

This prevents overselling.

---

# 16. Data Consistency

Strong consistency is used only when required.

Eventually consistent reads are sufficient for:

- Product Search
- Warehouse Search

Strongly consistent reads are used for:

- Inventory Reservation
- Order Creation

---

# 17. Backup Strategy

DynamoDB

- Point-in-Time Recovery
- Continuous Backup

Amazon S3

- Versioning
- Lifecycle Policies

---

# 18. Future Enhancements

- Single-table DynamoDB design
- Composite Keys
- GSIs for analytics
- DynamoDB Streams
- Global Tables
- TTL for temporary records

---

# 19. Summary

The Warehouse Management System uses DynamoDB because it offers:

- High throughput
- Low latency
- Automatic scaling
- Serverless architecture
- AWS SDK integration
- Seamless cloud deployment

The database design is optimized around **access patterns**, ensuring efficient lookups and scalable performance for warehouse operations.