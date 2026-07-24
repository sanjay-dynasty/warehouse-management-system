package com.cosmos.warehouse_management_system.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateInventoryRequest {

    @NotNull(message = "Reorder level is required")
    @Min(value = 0, message = "Reorder level can't be negative")
    private Integer reorderLevel;
    
}
