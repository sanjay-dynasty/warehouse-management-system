package com.cosmos.warehouse_management_system.warehouse.repository;

import com.cosmos.warehouse_management_system.warehouse.entity.Warehouse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WarehouseRepositoryImpl implements WarehouseRepository {

    private final DynamoDbTable<Warehouse> warehouseTable;

    @Override
    public Warehouse save(Warehouse warehouse) {

        warehouseTable.putItem(warehouse);

        return warehouse;
    }

    @Override
    public Optional<Warehouse> findById(String warehouseId) {

        Warehouse warehouse = warehouseTable.getItem(
                Key.builder()
                        .partitionValue(warehouseId)
                        .build());

        return Optional.ofNullable(warehouse);
    }

    @Override
    public List<Warehouse> findAll() {

        PageIterable<Warehouse> pages = warehouseTable.scan();

        return pages.items()
                .stream()
                .toList();
    }

    @Override
    public Warehouse update(Warehouse warehouse) {

        warehouseTable.updateItem(warehouse);

        return warehouse;
    }

    @Override
    public void delete(String warehouseId) {

        warehouseTable.deleteItem(
                Key.builder()
                        .partitionValue(warehouseId)
                        .build());
    }
}