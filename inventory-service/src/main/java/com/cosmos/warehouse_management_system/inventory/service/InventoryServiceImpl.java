package com.cosmos.warehouse_management_system.inventory.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cosmos.warehouse_management_system.common.constants.AppConstants;
import com.cosmos.warehouse_management_system.common.exception.BadRequestException;
import com.cosmos.warehouse_management_system.common.exception.ConflictException;
import com.cosmos.warehouse_management_system.common.util.DateUtil;
import com.cosmos.warehouse_management_system.common.exception.ResourceNotFoundException;
import com.cosmos.warehouse_management_system.inventory.dto.AddStockRequest;
import com.cosmos.warehouse_management_system.inventory.dto.CompleteOrderRequest;
import com.cosmos.warehouse_management_system.inventory.dto.CreateInventoryRequest;
import com.cosmos.warehouse_management_system.inventory.dto.InventoryResponse;
import com.cosmos.warehouse_management_system.inventory.dto.ReleaseStockRequest;
import com.cosmos.warehouse_management_system.inventory.dto.RemoveStockRequest;
import com.cosmos.warehouse_management_system.inventory.dto.ReserveStockRequest;
import com.cosmos.warehouse_management_system.inventory.dto.TransferStockRequest;
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
        inventory.setReservedQuantity(0);
        inventory.setCreatedAt(DateUtil.getCurrentTimestamp());
        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        repository.save(inventory);

        log.info("Inventory created successfully with Id :{} ", inventory.getInventoryId());

        return mapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse getInventoryById(String inventoryId) {
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

    @Override
    public InventoryResponse addStock(String inventoryId,
            AddStockRequest request) {

        log.info("Adding stock to inventory with id: {}", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found with id: {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + request.getQuantity());

        inventory.setTotalQuantity(
                inventory.getTotalQuantity() + request.getQuantity());

        inventory.setLastRestockedAt(DateUtil.getCurrentTimestamp());
        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        Inventory updatedInventory = repository.update(inventory);

        log.info("Stock added successfully to inventory: {}", inventoryId);

        return mapper.toResponse(updatedInventory);
    }

    @Override
    public InventoryResponse removeStock(String inventoryId,
            RemoveStockRequest request) {

        log.info("Removing stock from inventory with id: {}", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found with id: {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        if (inventory.getAvailableQuantity() < request.getQuantity()) {
            log.warn(
                    "Insufficient available stock. Inventory Id: {}, Available: {}, Requested: {}",
                    inventoryId,
                    inventory.getAvailableQuantity(),
                    request.getQuantity());

            throw new BadRequestException("Insufficient available stock.");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - request.getQuantity());

        inventory.setTotalQuantity(
                inventory.getTotalQuantity() - request.getQuantity());

        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        Inventory updatedInventory = repository.update(inventory);

        log.info("Stock removed successfully from inventory: {}", inventoryId);

        return mapper.toResponse(updatedInventory);
    }

    @Override
    public InventoryResponse reserveStock(String inventoryId,
            ReserveStockRequest request) {

        log.info("Reserving stock for inventory with id: {}", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found with id: {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        if (inventory.getAvailableQuantity() < request.getQuantity()) {

            log.warn(
                    "Insufficient available stock. Inventory Id: {}, Available: {}, Requested: {}",
                    inventoryId,
                    inventory.getAvailableQuantity(),
                    request.getQuantity());

            throw new BadRequestException("Insufficient available stock.");
        }

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - request.getQuantity());

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() + request.getQuantity());

        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        Inventory updatedInventory = repository.update(inventory);

        log.info("Stock reserved successfully for inventory: {}", inventoryId);

        return mapper.toResponse(updatedInventory);
    }

    @Override
    public InventoryResponse releaseReservedStock(String inventoryId,
            ReleaseStockRequest request) {

        log.info("Releasing reserved stock for inventory with id: {}", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found with id: {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        if (inventory.getReservedQuantity() < request.getQuantity()) {

            log.warn(
                    "Insufficient reserved stock. Inventory Id: {}, Reserved: {}, Requested: {}",
                    inventoryId,
                    inventory.getReservedQuantity(),
                    request.getQuantity());

            throw new BadRequestException("Insufficient reserved stock.");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - request.getQuantity());

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + request.getQuantity());

        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        Inventory updatedInventory = repository.update(inventory);

        log.info("Reserved stock released successfully for inventory: {}", inventoryId);

        return mapper.toResponse(updatedInventory);
    }

    @Override
    public InventoryResponse completeOrder(String inventoryId,
            CompleteOrderRequest request) {

        log.info("Completing order for inventory with id: {}", inventoryId);

        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> {
                    log.warn("Inventory not found with id: {}", inventoryId);
                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            inventoryId);
                });

        if (inventory.getReservedQuantity() < request.getQuantity()) {

            log.warn(
                    "Insufficient reserved stock. Inventory Id: {}, Reserved: {}, Requested: {}",
                    inventoryId,
                    inventory.getReservedQuantity(),
                    request.getQuantity());

            throw new BadRequestException("Insufficient reserved stock.");
        }

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - request.getQuantity());

        inventory.setTotalQuantity(
                inventory.getTotalQuantity() - request.getQuantity());

        inventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        Inventory updatedInventory = repository.update(inventory);

        log.info("Order completed successfully for inventory: {}", inventoryId);

        return mapper.toResponse(updatedInventory);
    }

    @Override
    public InventoryResponse transferStock(TransferStockRequest request) {

        log.info("Transferring {} units from inventory {} to {}",
                request.getQuantity(),
                request.getSourceInventoryId(),
                request.getDestinationInventoryId());

        Inventory sourceInventory = repository.findById(request.getSourceInventoryId())
                .orElseThrow(() -> {
                    log.warn("Source inventory not found with id: {}",
                            request.getSourceInventoryId());

                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            request.getSourceInventoryId());
                });

        Inventory destinationInventory = repository.findById(request.getDestinationInventoryId())
                .orElseThrow(() -> {
                    log.warn("Destination inventory not found with id: {}",
                            request.getDestinationInventoryId());

                    return new ResourceNotFoundException(
                            AppConstants.INVENTORY,
                            "inventoryId",
                            request.getDestinationInventoryId());
                });

        if (sourceInventory.getWarehouseId().equals(destinationInventory.getWarehouseId())) {
            throw new BadRequestException("Source and destination warehouses cannot be the same.");
        }

        if (!sourceInventory.getProductId().equals(destinationInventory.getProductId())) {
            throw new BadRequestException("Stock can only be transferred between inventories of the same product.");
        }

        if (sourceInventory.getAvailableQuantity() < request.getQuantity()) {
            throw new BadRequestException("Insufficient available stock.");
        }

        sourceInventory.setAvailableQuantity(
                sourceInventory.getAvailableQuantity() - request.getQuantity());

        sourceInventory.setTotalQuantity(
                sourceInventory.getTotalQuantity() - request.getQuantity());

        sourceInventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        destinationInventory.setAvailableQuantity(
                destinationInventory.getAvailableQuantity() + request.getQuantity());

        destinationInventory.setTotalQuantity(
                destinationInventory.getTotalQuantity() + request.getQuantity());

        destinationInventory.setLastRestockedAt(DateUtil.getCurrentTimestamp());
        destinationInventory.setUpdatedAt(DateUtil.getCurrentTimestamp());

        repository.update(sourceInventory);
        Inventory updatedDestination = repository.update(destinationInventory);

        log.info(
                "Transferred {} units from inventory {} to {}",
                request.getQuantity(),
                request.getSourceInventoryId(),
                request.getDestinationInventoryId());

        return mapper.toResponse(updatedDestination);
    }

}
