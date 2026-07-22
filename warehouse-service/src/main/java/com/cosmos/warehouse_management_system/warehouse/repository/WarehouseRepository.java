package com.cosmos.warehouse_management_system.warehouse.repository;

import com.cosmos.warehouse_management_system.warehouse.entity.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository {

    Warehouse save(Warehouse warehouse);

    Optional<Warehouse> findById(String warehouseId);

    List<Warehouse> findAll();

    Warehouse update(Warehouse warehouse);

    void delete(String warehouseId);

}