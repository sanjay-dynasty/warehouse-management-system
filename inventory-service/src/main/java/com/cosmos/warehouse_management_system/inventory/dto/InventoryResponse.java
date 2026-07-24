package com.cosmos.warehouse_management_system.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryResponse {

    private String inventoryId;
    private String productId;
    private String warehouseId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;
    private Integer reorderLevel;
    private String lastRestockedAt;
    private String createdAt;
    private String updatedAt;

}