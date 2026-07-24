package com.cosmos.warehouse_management_system.inventory.repository;

import com.cosmos.warehouse_management_system.inventory.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {

    Inventory save(Inventory inventory);

    Optional<Inventory> findById(String inventoryId);

    List<Inventory> findAll();

    Inventory update(Inventory inventory);

    void delete(String inventoryId);

    Optional<Inventory> findByProductIdAndWarehouseId(
            String productId,
            String warehouseId);

}