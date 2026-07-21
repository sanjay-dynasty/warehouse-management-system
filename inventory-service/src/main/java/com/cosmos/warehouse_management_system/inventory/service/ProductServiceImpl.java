package com.cosmos.warehouse_management_system.inventory.service;

import com.cosmos.warehouse_management_system.common.constants.AppConstants;
import com.cosmos.warehouse_management_system.common.exception.ResourceNotFoundException;
import com.cosmos.warehouse_management_system.inventory.dto.CreateProductRequest;
import com.cosmos.warehouse_management_system.inventory.dto.ProductResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateProductRequest;
import com.cosmos.warehouse_management_system.inventory.entity.Product;
import com.cosmos.warehouse_management_system.inventory.mapper.ProductMapper;
import com.cosmos.warehouse_management_system.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        log.info("Creating product: {}", request.getName());

        Product product = productMapper.toEntity(request);

        productRepository.save(product);

        log.info("Product created successfully with id: {}", product.getProductId());

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductById(String productId) {

        log.info("Fetching product with id: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", productId);
                    return new ResourceNotFoundException(
                            AppConstants.PRODUCT,
                            "productId",
                            productId);
                });

        log.info("Product fetched successfully");

        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        log.info("Fetching all products");

        List<ProductResponse> products = productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();

        log.info("Total products found: {}", products.size());

        return products;
    }

    @Override
    public ProductResponse updateProduct(String productId,
            UpdateProductRequest request) {

        log.info("Updating product with id: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", productId);
                    return new ResourceNotFoundException(
                            AppConstants.PRODUCT,
                            "productId",
                            productId);
                });

        productMapper.updateEntity(product, request);

        product.setUpdatedAt(LocalDateTime.now());

        productRepository.update(product);

        log.info("Product updated successfully");

        return productMapper.toResponse(product);
    }

    @Override
    public void deleteProduct(String productId) {

        log.info("Deleting product with id: {}", productId);

        productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found with id: {}", productId);
                    return new ResourceNotFoundException(
                            AppConstants.PRODUCT,
                            "productId",
                            productId);
                });

        productRepository.delete(productId);

        log.info("Product deleted successfully");
    }
}