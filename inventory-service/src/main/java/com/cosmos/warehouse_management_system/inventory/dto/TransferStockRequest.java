package com.cosmos.warehouse_management_system.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferStockRequest {

    @NotBlank(message = "Source inventory id is required.")
    private String sourceInventoryId;

    @NotBlank(message = "Destination inventory id is required.")
    private String destinationInventoryId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be greater than zero.")
    private Integer quantity;
}