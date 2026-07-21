package com.cosmos.warehouse_management_system.inventory.repository;

import com.cosmos.warehouse_management_system.inventory.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final DynamoDbTable<Product> productTable;

    public ProductRepositoryImpl(DynamoDbEnhancedClient enhancedClient,
            @Value("${dynamodb.product-table}") String tableName) {

        this.productTable = enhancedClient.table(
                tableName,
                TableSchema.fromBean(Product.class));
    }

    @Override
    public Product save(Product product) {

        log.info("Saving product with id {}", product.getProductId());
        productTable.putItem(product);
        return product;
    }

    @Override
    public Optional<Product> findById(String productId) {

        Product product = productTable.getItem(Key.builder()
                .partitionValue(productId)
                .build());

        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {

        return productTable.scan()
                .items()
                .stream()
                .toList();
    }

    @Override
    public Product update(Product product) {

        log.info("Updating product {}", product.getProductId());
        productTable.updateItem(product);
        return product;
    }

    @Override
    public void delete(String productId) {

        log.info("Deleting product {}", productId);
        productTable.deleteItem(Key.builder()
                .partitionValue(productId)
                .build());
    }
}