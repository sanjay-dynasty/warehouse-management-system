package com.cosmos.warehouse_management_system.inventory.controller;

import com.cosmos.warehouse_management_system.common.response.ApiResponse;
import com.cosmos.warehouse_management_system.inventory.dto.*;
import com.cosmos.warehouse_management_system.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> createInventory(
            @Valid @RequestBody CreateInventoryRequest request) {

        InventoryResponse response = inventoryService.createInventory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Inventory created successfully.",
                        response));
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventoryById(
            @PathVariable String inventoryId) {

        InventoryResponse response = inventoryService.getInventoryById(inventoryId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inventory fetched successfully.",
                        response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<java.util.List<InventoryResponse>>> getAllInventories() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inventories fetched successfully.",
                        inventoryService.getAllInventories()));
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse<InventoryResponse>> updateInventory(
            @PathVariable String inventoryId,
            @Valid @RequestBody UpdateInventoryRequest request) {

        InventoryResponse response = inventoryService.updateInventory(inventoryId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inventory updated successfully.",
                        response));
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteInventory(
            @PathVariable String inventoryId) {

        inventoryService.deleteInventory(inventoryId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inventory deleted successfully.",
                        null));
    }

    @PostMapping("/{inventoryId}/add-stock")
    public ResponseEntity<ApiResponse<InventoryResponse>> addStock(
            @PathVariable String inventoryId,
            @Valid @RequestBody AddStockRequest request) {

        InventoryResponse response = inventoryService.addStock(inventoryId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock added successfully.",
                        response));
    }

    @PostMapping("/{inventoryId}/remove-stock")
    public ResponseEntity<ApiResponse<InventoryResponse>> removeStock(
            @PathVariable String inventoryId,
            @Valid @RequestBody RemoveStockRequest request) {

        InventoryResponse response = inventoryService.removeStock(inventoryId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock removed successfully.",
                        response));
    }

    @PostMapping("/{inventoryId}/reserve")
    public ResponseEntity<ApiResponse<InventoryResponse>> reserveStock(
            @PathVariable String inventoryId,
            @Valid @RequestBody ReserveStockRequest request) {

        InventoryResponse response = inventoryService.reserveStock(inventoryId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock reserved successfully.",
                        response));
    }

    @PostMapping("/{inventoryId}/release")
    public ResponseEntity<ApiResponse<InventoryResponse>> releaseReservedStock(
            @PathVariable String inventoryId,
            @Valid @RequestBody ReleaseStockRequest request) {

        InventoryResponse response = inventoryService.releaseReservedStock(inventoryId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Reserved stock released successfully.",
                        response));
    }

    @PostMapping("/{inventoryId}/complete-order")
    public ResponseEntity<ApiResponse<InventoryResponse>> completeOrder(
            @PathVariable String inventoryId,
            @Valid @RequestBody CompleteOrderRequest request) {

        InventoryResponse response = inventoryService.completeOrder(inventoryId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order completed successfully.",
                        response));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<InventoryResponse>> transferStock(
            @Valid @RequestBody TransferStockRequest request) {

        InventoryResponse response = inventoryService.transferStock(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock transferred successfully.",
                        response));
    }
}