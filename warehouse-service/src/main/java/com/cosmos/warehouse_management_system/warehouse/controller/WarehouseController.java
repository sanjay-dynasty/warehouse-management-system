package com.cosmos.warehouse_management_system.warehouse.controller;

import com.cosmos.warehouse_management_system.common.response.ApiResponse;
import com.cosmos.warehouse_management_system.warehouse.dto.CreateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.UpdateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.WarehouseResponse;
import com.cosmos.warehouse_management_system.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ApiResponse<WarehouseResponse> createWarehouse(
            @Valid @RequestBody CreateWarehouseRequest request) {

        return ApiResponse.success(
                "Warehouse created successfully.",
                warehouseService.createWarehouse(request));
    }

    @GetMapping("/{warehouseId}")
    public ApiResponse<WarehouseResponse> getWarehouseById(
            @PathVariable String warehouseId) {

        return ApiResponse.success(
                "Warehouse fetched successfully.",
                warehouseService.getWarehouseById(warehouseId));
    }

    @GetMapping
    public ApiResponse<List<WarehouseResponse>> getAllWarehouses() {

        return ApiResponse.success(
                "Warehouses fetched successfully.",
                warehouseService.getAllWarehouses());
    }

    @PutMapping("/{warehouseId}")
    public ApiResponse<WarehouseResponse> updateWarehouse(
            @PathVariable String warehouseId,
            @Valid @RequestBody UpdateWarehouseRequest request) {

        return ApiResponse.success(
                "Warehouse updated successfully.",
                warehouseService.updateWarehouse(warehouseId, request));
    }

    @DeleteMapping("/{warehouseId}")
    public ApiResponse<Void> deleteWarehouse(
            @PathVariable String warehouseId) {

        warehouseService.deleteWarehouse(warehouseId);

        return ApiResponse.success(
                "Warehouse deleted successfully.",
                null);
    }

}