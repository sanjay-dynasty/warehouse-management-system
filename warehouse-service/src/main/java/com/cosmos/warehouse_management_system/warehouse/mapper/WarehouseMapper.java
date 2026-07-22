package com.cosmos.warehouse_management_system.warehouse.mapper;

import com.cosmos.warehouse_management_system.warehouse.dto.CreateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.UpdateWarehouseRequest;
import com.cosmos.warehouse_management_system.warehouse.dto.WarehouseResponse;
import com.cosmos.warehouse_management_system.warehouse.entity.Warehouse;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {

    public Warehouse toEntity(CreateWarehouseRequest request) {

        return Warehouse.builder()
                .name(request.getName())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .capacity(request.getCapacity())
                .status(request.getStatus())
                .build();
    }

    public WarehouseResponse toResponse(Warehouse warehouse) {

        return WarehouseResponse.builder()
                .warehouseId(warehouse.getWarehouseId())
                .name(warehouse.getName())
                .addressLine1(warehouse.getAddressLine1())
                .addressLine2(warehouse.getAddressLine2())
                .city(warehouse.getCity())
                .state(warehouse.getState())
                .country(warehouse.getCountry())
                .postalCode(warehouse.getPostalCode())
                .contactNumber(warehouse.getContactNumber())
                .email(warehouse.getEmail())
                .capacity(warehouse.getCapacity())
                .status(warehouse.getStatus())
                .createdAt(warehouse.getCreatedAt())
                .updatedAt(warehouse.getUpdatedAt())
                .build();
    }

    public void updateEntity(Warehouse warehouse, UpdateWarehouseRequest request) {

        warehouse.setName(request.getName());
        warehouse.setAddressLine1(request.getAddressLine1());
        warehouse.setAddressLine2(request.getAddressLine2());
        warehouse.setCity(request.getCity());
        warehouse.setState(request.getState());
        warehouse.setCountry(request.getCountry());
        warehouse.setPostalCode(request.getPostalCode());
        warehouse.setContactNumber(request.getContactNumber());
        warehouse.setEmail(request.getEmail());
        warehouse.setCapacity(request.getCapacity());
        warehouse.setStatus(request.getStatus());
    }

}