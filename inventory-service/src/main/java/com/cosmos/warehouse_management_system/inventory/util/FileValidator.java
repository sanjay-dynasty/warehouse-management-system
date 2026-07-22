package com.cosmos.warehouse_management_system.inventory.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cosmos.warehouse_management_system.common.exception.InvalidFileException;

import java.util.List;

@Component
public class FileValidator {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/webp");

    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            "jpg",
            "jpeg",
            "png",
            "webp");

    public void validate(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("File cannot be empty.");
        }

        validateSize(file);

        validateContentType(file);

        validateExtension(file);
    }

    private void validateSize(MultipartFile file) {

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidFileException(
                    "Maximum allowed file size is 5 MB.");
        }
    }

    private void validateContentType(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType == null ||
                !ALLOWED_CONTENT_TYPES.contains(contentType)) {

            throw new InvalidFileException(
                    "Only JPG, JPEG, PNG and WEBP images are allowed.");
        }
    }

    private void validateExtension(MultipartFile file) {

        String filename = file.getOriginalFilename();

        if (filename == null || !filename.contains(".")) {
            throw new InvalidFileException("Invalid file name.");
        }

        String extension = filename.substring(filename.lastIndexOf('.') + 1)
                .toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new InvalidFileException(
                    "Invalid file extension.");
        }
    }

}