package com.cosmos.warehouse_management_system.inventory.service;

import com.cosmos.warehouse_management_system.common.constants.AppConstants;
import com.cosmos.warehouse_management_system.common.exception.InvalidFileException;
import com.cosmos.warehouse_management_system.inventory.util.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    private final S3Client s3Client;
    private final FileValidator fileValidator;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile file) {

        fileValidator.validate(file);

        String objectKey = generateObjectKey(file.getOriginalFilename());

        try {

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(file.getBytes()));

            log.info("Image uploaded successfully to S3 with key: {}", objectKey);

            return getImageUrl(objectKey);

        } catch (IOException ex) {

            log.error("Failed to upload image to S3", ex);

            throw new InvalidFileException("Unable to upload image.");
        }
    }

    @Override
    public void deleteImage(String objectKey) {

        if (objectKey == null || objectKey.isBlank()) {
            return;
        }

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        s3Client.deleteObject(request);

        log.info("Deleted image from S3: {}", objectKey);
    }

    @Override
    public String getImageUrl(String objectKey) {

        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                s3Client.serviceClientConfiguration()
                        .region()
                        .id(),
                objectKey);
    }

    @Override
    public String generateObjectKey(String originalFilename) {

        String filename = "image";
        String extension = "";

        if (originalFilename != null && !originalFilename.isBlank()) {

            int dotIndex = originalFilename.lastIndexOf('.');

            if (dotIndex > 0) {
                filename = originalFilename.substring(0, dotIndex);
                extension = originalFilename.substring(dotIndex);
            } else {
                filename = originalFilename;
            }
        }

        filename = filename
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");

        return String.format(
                "%s/%s_%s%s",
                AppConstants.PRODUCT_IMAGES,
                filename,
                UUID.randomUUID(),
                extension.toLowerCase());
    }

    @Override
    public String extractObjectKey(String imageUrl) {

        if (imageUrl == null || imageUrl.isBlank()) {
            return null;
        }

        int index = imageUrl.indexOf(".amazonaws.com/");

        if (index == -1) {
            return null;
        }

        return imageUrl.substring(index + ".amazonaws.com/".length());
    }
}