package com.cosmos.warehouse_management_system.inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Inventory {

    private String inventoryId;
    private String productId;
    private String warehouseId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;
    private Integer reorderLevel;
    private String lastRestockedAt;
    private String createdAt;
    private String updatedAt;

    @DynamoDbPartitionKey
    public String getInventoryId() {
        return inventoryId;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "product-warehouse-index")
    public String getProductId() {
        return productId;
    }

    @DynamoDbSecondarySortKey(indexNames = "product-warehouse-index")
    public String getWarehouseId() {
        return warehouseId;
    }

}
