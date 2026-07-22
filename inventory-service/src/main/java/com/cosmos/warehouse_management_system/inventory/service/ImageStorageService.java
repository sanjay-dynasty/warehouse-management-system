package com.cosmos.warehouse_management_system.inventory.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String uploadImage(MultipartFile file);

    void deleteImage(String objectKey);

    String getImageUrl(String objectKey);

    String generateObjectKey(String originalFilename);

    String extractObjectKey(String imageUrl);
}