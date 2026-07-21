package com.cosmos.warehouse_management_system.inventory.mapper;

import com.cosmos.warehouse_management_system.inventory.dto.CreateProductRequest;
import com.cosmos.warehouse_management_system.inventory.dto.ProductResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateProductRequest;
import com.cosmos.warehouse_management_system.inventory.entity.Product;
import com.cosmos.warehouse_management_system.inventory.entity.enums.ProductStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProductMapper {

    public Product toEntity(CreateProductRequest request) {

        return Product.builder()
                .productId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .brand(request.getBrand())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .status(ProductStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .brand(product.getBrand())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .status(product.getStatus())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public void updateEntity(Product product, UpdateProductRequest request) {

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setCurrency(request.getCurrency());
        product.setStatus(request.getStatus());
    }
}