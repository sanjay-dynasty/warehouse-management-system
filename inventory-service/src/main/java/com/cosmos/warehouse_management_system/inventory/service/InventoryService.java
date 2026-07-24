package com.cosmos.warehouse_management_system.inventory.service;

import java.util.List;

import com.cosmos.warehouse_management_system.inventory.dto.CreateInventoryRequest;
import com.cosmos.warehouse_management_system.inventory.dto.InventoryResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateInventoryRequest;

public interface InventoryService {

    InventoryResponse createInventory(CreateInventoryRequest request);

    InventoryResponse getInventoryResponseById(String inventoryId);

    List<InventoryResponse> getAllInventories();

    InventoryResponse updateInventory(String inventoryId, UpdateInventoryRequest request);

    void deleteInventory(String inventoryId);
    
}
