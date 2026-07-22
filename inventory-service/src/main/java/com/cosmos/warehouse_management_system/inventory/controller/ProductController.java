package com.cosmos.warehouse_management_system.inventory.controller;

import com.cosmos.warehouse_management_system.common.response.ApiResponse;
import com.cosmos.warehouse_management_system.inventory.dto.CreateProductRequest;
import com.cosmos.warehouse_management_system.inventory.dto.ProductResponse;
import com.cosmos.warehouse_management_system.inventory.dto.UpdateProductRequest;
import com.cosmos.warehouse_management_system.inventory.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Product API", description = "Inventory Product Management APIs")
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

        private final ProductService productService;

        @Operation(summary = "Create Product")
        @PostMapping
        public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
                        @Valid @RequestBody CreateProductRequest request) {

                log.info("Received request to create product");
                ProductResponse response = productService.createProduct(request);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ApiResponse.success(
                                                "Product created successfully",
                                                response));
        }

        @Operation(summary = "Get Product By Id")
        @GetMapping("/{productId}")
        public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
                        @PathVariable String productId) {

                log.info("Received request to fetch product {}", productId);
                ProductResponse response = productService.getProductById(productId);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Product fetched successfully",
                                                response));
        }

        @Operation(summary = "Get All Products")
        @GetMapping
        public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {

                log.info("Received request to fetch all products");
                List<ProductResponse> response = productService.getAllProducts();

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Products fetched successfully",
                                                response));
        }

        @Operation(summary = "Update Product")
        @PutMapping("/{productId}")
        public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
                        @PathVariable String productId,
                        @Valid @RequestBody UpdateProductRequest request) {

                log.info("Received request to update product {}", productId);
                ProductResponse response = productService.updateProduct(productId, request);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Product updated successfully",
                                                response));
        }

        @Operation(summary = "Delete Product")
        @DeleteMapping("/{productId}")
        public ResponseEntity<ApiResponse<Void>> deleteProduct(
                        @PathVariable String productId) {

                log.info("Received request to delete product {}", productId);
                productService.deleteProduct(productId);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Product deleted successfully",
                                                null));
        }

        @PostMapping(value = "/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<ApiResponse<ProductResponse>> uploadImage(

                        @PathVariable String productId,

                        @RequestPart MultipartFile file) {

                ProductResponse response = productService.uploadProductImage(
                                productId,
                                file);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Image uploaded successfully",
                                                response));
        }

        @DeleteMapping("/{productId}/image")
        public ResponseEntity<ApiResponse<ProductResponse>> deleteProductImage(
                        @PathVariable String productId) {

                ProductResponse response = productService.deleteProductImage(productId);

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                "Product image deleted successfully",
                                                response));
        }
}