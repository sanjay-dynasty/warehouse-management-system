package com.cosmos.warehouse_management_system.inventory.dto;

import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductCategory;
import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponse {

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
}