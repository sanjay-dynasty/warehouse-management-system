package com.cosmos.warehouse_management_system.inventory.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cosmos.warehouse_management_system.common.constants.AppConstants;
import com.cosmos.warehouse_management_system.common.exception.ConflictException;
import com.cosmos.warehouse_management_system.common.util.DateUtil;
import com.cosmos.warehouse_management_system.common.exception.ResourceNotFoundException;
import com.cosmos.warehouse_management_system.inventory.dto.CreateInventoryRequest;
import com.cosmos.warehouse_management_system.inventory.dto.InventoryResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateInventoryRequest;
import com.cosmos.warehouse_management_system.inventory.entity.Inventory;
import com.cosmos.warehouse_management_system.inventory.mapper.InventoryMapper;
import com.cosmos.warehouse_management_system.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) {

        log.info("Creating inventory for ProductId : {} in warehouse {}", request.getProductId(),
                request.getWarehouseId());

        repository.findByProductIdAndWarehouseId(request.getProductId(), request.getWarehouseId())
                .ifPresent(inventory -> {
                    throw new ConflictException("Inventory already exists.");
                });

        Inventory inventory = mapper.toEntity(request);
        inventory.setInventoryId(UUID.randomUUID().toString());
        inventory.setAvailableQuantity(request.getTotalQuantity());
        inventory.setReservedQuantity(request.getTotalQuantity());
        inventory.setReservedQuantity(0);
        inventory.setCreatedAt(DateUtil.getCurrentTimestamp());
        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        repository.save(inventory);

        log.info("Inventory created successfully with Id :{} ", inventory.getInventoryId());

        return mapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse getInventoryResponseById(String inventoryId) {
        log.info("Fetching Inventory : {} ", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found {} : ", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "InventoryId",
                            inventoryId);
                });

        return mapper.toResponse(inventory);

    }

    @Override
    public List<InventoryResponse> getAllInventories() {

        log.info("Fetching all inventories");

        List<InventoryResponse> inventories = repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

        log.info("Total inventories: {}", inventories.size());

        return inventories;
    }

    @Override
    public InventoryResponse updateInventory(
            String inventoryId,
            UpdateInventoryRequest request) {

        log.info("Updating inventory {}", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        mapper.updateEntity(inventory, request);

        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        repository.update(inventory);

        log.info("Inventory updated successfully");

        return mapper.toResponse(inventory);
    }

    @Override
    public void deleteInventory(String inventoryId) {

        log.info("Deleting inventory {}", inventoryId);

        repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        repository.delete(inventoryId);

        log.info("Inventory deleted successfully");
    }

}
