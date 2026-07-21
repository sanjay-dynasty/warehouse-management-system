package com.cosmos.warehouse_management_system.inventory.repository;

import com.cosmos.warehouse_management_system.inventory.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String productId);

    List<Product> findAll();

    Product update(Product product);

    void delete(String productId);
}