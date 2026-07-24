package com.cosmos.warehouse_management_system.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInventoryRequest {

    @NotBlank(message = "Product Id is required")
    private String productId;

    @NotBlank(message = "Inventory Id is required")
    private String warehouseId;

    @NotNull(message = "Total quantity is required")
    @Min(value = 0, message = "Quantity can't be negative")
    private Integer totalQuantity;

    @NotNull(message = "Reorder level is required")
    @Min(value = 0, message = "Reorder level can't be negative")
    private Integer reorderLevel;
}
