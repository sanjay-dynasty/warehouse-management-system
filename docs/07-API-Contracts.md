# API Contracts

## Version

| Property       | Value                             |
| -------------- | --------------------------------- |
| Project        | Warehouse Management System (WMS) |
| Version        | v1                                |
| API Style      | REST                              |
| Data Format    | JSON                              |
| Authentication | JWT Bearer Token                  |
| Content-Type   | application/json                  |

---

# Base URL

```
http://localhost:8080/api/v1
```

Production

```
https://api.wms.com/api/v1
```

---

# Authentication

All APIs (except Login) require JWT Token.

```
Authorization: Bearer <JWT_TOKEN>
```

---

# Standard HTTP Status Codes

| Code | Meaning               |
| ---- | --------------------- |
| 200  | Success               |
| 201  | Created               |
| 204  | No Content            |
| 400  | Bad Request           |
| 401  | Unauthorized          |
| 403  | Forbidden             |
| 404  | Not Found             |
| 409  | Conflict              |
| 500  | Internal Server Error |

---

# Standard API Response

## Success

```json
{
    "success": true,
    "message": "Product created successfully",
    "data": {},
    "timestamp": "2026-07-20T10:30:00Z"
}
```

---

## Error

```json
{
    "success": false,
    "message": "Validation Failed",
    "errors": [
        "Product Name is required"
    ],
    "timestamp": "2026-07-20T10:30:00Z"
}
```

---

# Inventory APIs

---

## Create Product

### Endpoint

```
POST /products
```

### Description

Creates a new product.

---

### Request

```json
{
    "name":"Laptop",
    "description":"Dell Latitude",
    "category":"Electronics",
    "price":65000,
    "stock":25,
    "warehouseId":"WH001"
}
```

---

### Validation

| Field       | Rule     |
| ----------- | -------- |
| name        | Required |
| category    | Required |
| price       | >0       |
| stock       | >=0      |
| warehouseId | Required |

---

### Response

```json
{
    "success":true,
    "message":"Product Created",
    "data":{
        "productId":"P1001"
    }
}
```

---

## Get Product

```
GET /products/{productId}
```

### Response

```json
{
    "productId":"P1001",
    "name":"Laptop",
    "category":"Electronics",
    "price":65000,
    "stock":25,
    "warehouseId":"WH001",
    "imageUrl":"https://s3.amazonaws.com/..."
}
```

---

## Update Product

```
PUT /products/{productId}
```

### Request

```json
{
    "price":70000,
    "stock":35
}
```

---

## Delete Product

```
DELETE /products/{productId}
```

Response

```
204 No Content
```

---

## Search Products

```
GET /products
```

### Query Parameters

```
?name=laptop

?category=electronics

?warehouseId=WH001

?page=0

&size=20

&sort=name
```

---

### Response

```json
{
    "content":[
        {
            "productId":"P1001",
            "name":"Laptop"
        }
    ],
    "page":0,
    "size":20,
    "totalElements":100
}
```

---

# Product Image APIs

---

## Upload Product Image

```
POST /products/{productId}/image
```

Content-Type

```
multipart/form-data
```

Request

```
image=<file>
```

Response

```json
{
    "imageUrl":"https://bucket.s3.amazonaws.com/products/P1001.jpg"
}
```

---

## Delete Product Image

```
DELETE /products/{productId}/image
```

---

# Warehouse APIs

---

## Create Warehouse

```
POST /warehouses
```

Request

```json
{
    "warehouseName":"Lucknow Warehouse",
    "city":"Lucknow",
    "state":"UP",
    "capacity":100000
}
```

---

Response

```json
{
    "warehouseId":"WH001"
}
```

---

## Get Warehouse

```
GET /warehouses/{warehouseId}
```

---

## Update Warehouse

```
PUT /warehouses/{warehouseId}
```

---

## Delete Warehouse

```
DELETE /warehouses/{warehouseId}
```

---

## Get All Warehouses

```
GET /warehouses
```

Supports

```
page

size

sort
```

---

# Bin APIs

---

## Create Bin

```
POST /warehouses/{warehouseId}/bins
```

Request

```json
{
    "aisle":"A",
    "rack":"R10",
    "shelf":"S05",
    "level":"L1"
}
```

---

## Get Bin

```
GET /bins/{binId}
```

---

## Allocate Product to Bin

```
PUT /bins/{binId}/allocate
```

Request

```json
{
    "productId":"P1001"
}
```

---

# Inventory APIs

---

## Add Stock

```
POST /inventory/add
```

Request

```json
{
    "productId":"P1001",
    "quantity":50
}
```

---

## Remove Stock

```
POST /inventory/remove
```

---

## Reserve Stock

```
POST /inventory/reserve
```

Request

```json
{
    "productId":"P1001",
    "quantity":2
}
```

---

## Release Stock

```
POST /inventory/release
```

---

# Order APIs

---

## Create Order

```
POST /orders
```

Request

```json
{
    "customerId":"C100",
    "productId":"P1001",
    "quantity":2
}
```

---

Response

```json
{
    "orderId":"ORD1001",
    "status":"CREATED"
}
```

---

## Get Order

```
GET /orders/{orderId}
```

---

## Cancel Order

```
PUT /orders/{orderId}/cancel
```

---

## Get Customer Orders

```
GET /customers/{customerId}/orders
```

Supports

```
page

size

sort
```

---

# Notification APIs

---

## Get Notifications

```
GET /notifications
```

---

## Get Notification By ID

```
GET /notifications/{notificationId}
```

---

# Report APIs

---

## Daily Inventory Report

```
GET /reports/inventory
```

---

## Warehouse Report

```
GET /reports/warehouse
```

---

## Low Stock Report

```
GET /reports/low-stock
```

---

## Top Selling Products

```
GET /reports/top-products
```

---

# Validation Rules

| Field        | Validation     |
| ------------ | -------------- |
| Product Name | Required       |
| Price        | Greater than 0 |
| Stock        | >=0            |
| Capacity     | >0             |
| Quantity     | >0             |
| Image Size   | <=10 MB        |
| Image Type   | JPG, PNG       |

---

# Error Codes

| Code                | Description            |
| ------------------- | ---------------------- |
| PRODUCT_NOT_FOUND   | Product doesn't exist  |
| WAREHOUSE_NOT_FOUND | Warehouse not found    |
| BIN_NOT_FOUND       | Bin not found          |
| ORDER_NOT_FOUND     | Order not found        |
| OUT_OF_STOCK        | Inventory unavailable  |
| INVALID_REQUEST     | Validation failed      |
| DUPLICATE_PRODUCT   | Product already exists |
| IMAGE_UPLOAD_FAILED | Unable to upload image |

---

# Pagination Standard

Request

```
?page=0&size=20&sort=name
```

Response

```json
{
    "content":[],
    "page":0,
    "size":20,
    "totalElements":150,
    "totalPages":8,
    "last":false
}
```

---

# Filtering Examples

```
GET /products?category=Electronics

GET /products?warehouseId=WH001

GET /products?stockLessThan=10

GET /orders?status=CREATED

GET /warehouses?city=Lucknow
```

---

# Idempotency

The following APIs should support idempotency using the `Idempotency-Key` header:

```
POST /orders

POST /inventory/reserve
```

Example

```
Idempotency-Key:
550e8400-e29b-41d4-a716-446655440000
```

Duplicate requests with the same key should return the original response instead of creating duplicate resources.

---

# API Versioning

Current Version

```
/api/v1
```

Future

```
/api/v2
```

---

# OpenAPI / Swagger

Swagger URL

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI

```
http://localhost:8080/v3/api-docs
```

---

# API Security

- JWT Authentication
- HTTPS Only
- Input Validation
- Role-Based Authorization
- Request Logging
- Rate Limiting
- CORS Configuration

---

# API Design Best Practices

- Resource-oriented URLs
- Proper HTTP methods
- Consistent response format
- Pagination for list APIs
- Filtering through query parameters
- Stateless APIs
- Idempotent operations where applicable
- Standard HTTP status codes
- Global exception handling
- OpenAPI documentation

---

# Summary

The Warehouse Management System exposes a versioned REST API that follows industry-standard REST principles. The APIs are secured using JWT authentication, documented using OpenAPI/Swagger, support pagination and filtering, and provide consistent request and response formats to simplify client integration and future extensibility.