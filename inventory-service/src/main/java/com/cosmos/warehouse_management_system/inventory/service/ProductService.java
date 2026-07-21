package com.cosmos.warehouse_management_system.inventory.service;

import com.cosmos.warehouse_management_system.inventory.dto.CreateProductRequest;
import com.cosmos.warehouse_management_system.inventory.dto.ProductResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProductById(String productId);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(String productId, UpdateProductRequest request);

    void deleteProduct(String productId);
}