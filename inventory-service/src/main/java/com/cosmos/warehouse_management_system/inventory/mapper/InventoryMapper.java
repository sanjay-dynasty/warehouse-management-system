package com.cosmos.warehouse_management_system.inventory.mapper;

import com.cosmos.warehouse_management_system.inventory.dto.CreateInventoryRequest;
import com.cosmos.warehouse_management_system.inventory.dto.InventoryResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateInventoryRequest;
import com.cosmos.warehouse_management_system.inventory.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public Inventory toEntity(CreateInventoryRequest request) {

        return Inventory.builder()
                .productId(request.getProductId())
                .warehouseId(request.getWarehouseId())
                .totalQuantity(request.getTotalQuantity())
                .availableQuantity(request.getTotalQuantity())
                .reservedQuantity(0)
                .reorderLevel(request.getReorderLevel())
                .build();
    }

    public InventoryResponse toResponse(Inventory inventory) {

        InventoryResponse response = new InventoryResponse();

        response.setInventoryId(inventory.getInventoryId());
        response.setProductId(inventory.getProductId());
        response.setWarehouseId(inventory.getWarehouseId());
        response.setAvailableQuantity(inventory.getAvailableQuantity());
        response.setReservedQuantity(inventory.getReservedQuantity());
        response.setTotalQuantity(inventory.getTotalQuantity());
        response.setReorderLevel(inventory.getReorderLevel());
        response.setLastRestockedAt(inventory.getLastRestockedAt());
        response.setCreatedAt(inventory.getCreatedAt());
        response.setUpdatedAt(inventory.getUpdatedAt());

        return response;
    }

    public void updateEntity(
            Inventory inventory,
            UpdateInventoryRequest request) {

        inventory.setReorderLevel(request.getReorderLevel());
    }

}