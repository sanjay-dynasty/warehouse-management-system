package com.cosmos.warehouse_management_system.warehouse.dto;

import com.cosmos.warehouse_management_system.common.enums.WarehouseStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseResponse {

    private String warehouseId;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String contactNumber;
    private String email;
    private Integer capacity;
    private WarehouseStatus status;
    private String createdAt;
    private String updatedAt;

}