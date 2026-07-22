package com.cosmos.warehouse_management_system.warehouse.service;

import com.cosmos.warehouse_management_system.common.constants.AppConstants;
import com.cosmos.warehouse_management_system.common.exception.ResourceNotFoundException;
import com.cosmos.warehouse_management_system.common.util.DateUtil;
import com.cosmos.warehouse_management_system.warehouse.dto.CreateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.UpdateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.WarehouseResponse;
import com.cosmos.warehouse_management_system.warehouse.entity.Warehouse;
import com.cosmos.warehouse_management_system.warehouse.mapper.WarehouseMapper;
import com.cosmos.warehouse_management_system.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public WarehouseResponse createWarehouse(CreateWarehouseRequest request) {

        log.info("Creating warehouse: {}", request.getName());

        Warehouse warehouse = warehouseMapper.toEntity(request);

        warehouse.setWarehouseId(UUID.randomUUID().toString());
        warehouse.setCreatedAt(DateUtil.getCurrentTimestamp());
        warehouse.setUpdatedAt(DateUtil.getCurrentTimestamp());

        warehouseRepository.save(warehouse);

        log.info("Warehouse created successfully with id: {}", warehouse.getWarehouseId());

        return warehouseMapper.toResponse(warehouse);
    }

    @Override
    public WarehouseResponse getWarehouseById(String warehouseId) {

        log.info("Fetching warehouse with id: {}", warehouseId);

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> {
                    log.warn("Warehouse not found with id: {}", warehouseId);
                    return new ResourceNotFoundException(
                            AppConstants.WAREHOUSE,
                            "warehouseId",
                            warehouseId);
                });

        log.info("Warehouse fetched successfully");

        return warehouseMapper.toResponse(warehouse);
    }

    @Override
    public List<WarehouseResponse> getAllWarehouses() {

        log.info("Fetching all warehouses");

        List<WarehouseResponse> warehouses = warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toResponse)
                .toList();

        log.info("Total warehouses found: {}", warehouses.size());

        return warehouses;
    }

    @Override
    public WarehouseResponse updateWarehouse(String warehouseId,
            UpdateWarehouseRequest request) {

        log.info("Updating warehouse with id: {}", warehouseId);

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> {
                    log.warn("Warehouse not found with id: {}", warehouseId);
                    return new ResourceNotFoundException(
                            AppConstants.WAREHOUSE,
                            "warehouseId",
                            warehouseId);
                });

        warehouseMapper.updateEntity(warehouse, request);

        warehouse.setUpdatedAt(DateUtil.getCurrentTimestamp());

        warehouseRepository.update(warehouse);

        log.info("Warehouse updated successfully");

        return warehouseMapper.toResponse(warehouse);
    }

    @Override
    public void deleteWarehouse(String warehouseId) {

        log.info("Deleting warehouse with id: {}", warehouseId);

        warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> {
                    log.warn("Warehouse not found with id: {}", warehouseId);
                    return new ResourceNotFoundException(
                            AppConstants.WAREHOUSE,
                            "warehouseId",
                            warehouseId);
                });

        warehouseRepository.delete(warehouseId);

        log.info("Warehouse deleted successfully");
    }
}