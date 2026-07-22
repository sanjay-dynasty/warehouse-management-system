package com.cosmos.warehouse_management_system.warehouse.service;

import com.cosmos.warehouse_management_system.warehouse.dto.CreateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.UpdateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.WarehouseResponse;

import java.util.List;

public interface WarehouseService {

    WarehouseResponse createWarehouse(CreateWarehouseRequest request);

    WarehouseResponse getWarehouseById(String warehouseId);

    List<WarehouseResponse> getAllWarehouses();

    WarehouseResponse updateWarehouse(String warehouseId, UpdateWarehouseRequest request);

    void deleteWarehouse(String warehouseId);

}