package com.cosmos.warehouse_management_system.warehouse.entity;

import com.cosmos.warehouse_management_system.common.enums.WarehouseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Warehouse {

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

    @DynamoDbPartitionKey
    public String getWarehouseId() {
        return warehouseId;
    }
}