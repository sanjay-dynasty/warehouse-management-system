package com.cosmos.warehouse_management_system.inventory.repository;

import com.cosmos.warehouse_management_system.inventory.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final DynamoDbTable<Inventory> inventoryTable;

    @Override
    public Inventory save(Inventory inventory) {

        inventoryTable.putItem(inventory);
        return inventory;
    }

    @Override
    public Optional<Inventory> findById(String inventoryId) {

        return Optional.ofNullable(
                inventoryTable.getItem(
                        Key.builder()
                                .partitionValue(inventoryId)
                                .build()));
    }

    @Override
    public List<Inventory> findAll() {

        return inventoryTable.scan()
                .items()
                .stream()
                .toList();
    }

    @Override
    public Inventory update(Inventory inventory) {

        inventoryTable.updateItem(inventory);
        return inventory;
    }

    @Override
    public void delete(String inventoryId) {

        inventoryTable.deleteItem(
                Key.builder()
                        .partitionValue(inventoryId)
                        .build());
    }

    @Override
    public Optional<Inventory> findByProductIdAndWarehouseId(
            String productId,
            String warehouseId) {

        DynamoDbIndex<Inventory> index = inventoryTable.index("product-warehouse-index");

        QueryConditional queryConditional = QueryConditional.keyEqualTo(
                Key.builder()
                        .partitionValue(productId)
                        .sortValue(warehouseId)
                        .build());

        return index.query(queryConditional)
                .stream()
                .flatMap(page -> page.items().stream())
                .findFirst();
    }
}