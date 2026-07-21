package com.cosmos.warehouse_management_system.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductCategory;
import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Product {

    private String productId;
    private String name;
    private String description;
    private ProductCategory category;
    private String brand;
    private BigDecimal price;
    private String currency;
    private ProductStatus status;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @DynamoDbPartitionKey
    public String getProductId() {
        return productId;
    }
}