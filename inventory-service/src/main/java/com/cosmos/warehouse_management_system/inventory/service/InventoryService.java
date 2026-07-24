package com.cosmos.warehouse_management_system.inventory.service;

import com.cosmos.warehouse_management_system.inventory.dto.*;

import java.util.List;

public interface InventoryService {

    InventoryResponse createInventory(CreateInventoryRequest request);

    InventoryResponse getInventoryById(String inventoryId);

    List<InventoryResponse> getAllInventories();

    InventoryResponse updateInventory(String inventoryId, UpdateInventoryRequest request);

    void deleteInventory(String inventoryId);

    InventoryResponse addStock(String inventoryId, AddStockRequest request);

    InventoryResponse removeStock(String inventoryId, RemoveStockRequest request);

    InventoryResponse reserveStock(String inventoryId, ReserveStockRequest request);

    InventoryResponse releaseReservedStock(String inventoryId, ReleaseStockRequest request);

    InventoryResponse completeOrder(String inventoryId, CompleteOrderRequest request);

    InventoryResponse transferStock(TransferStockRequest request);
}