package com.cosmos.warehouse_management_system.inventory.dto;

import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductCategory;
import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private ProductCategory category;

    @NotBlank
    private String brand;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotBlank
    private String currency;

    @NotNull
    private ProductStatus status;
}