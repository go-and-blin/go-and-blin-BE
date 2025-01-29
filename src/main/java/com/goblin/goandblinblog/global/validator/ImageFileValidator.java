package com.goblin.goandblinblog.global.validator;

import com.goblin.goandblinblog.global.exception.file.InvalidFileExtensionException;
import com.goblin.goandblinblog.global.exception.file.NotFoundFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public class ImageFileValidator {

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif");

    public static void validate(MultipartFile file) {
        validateFilePresence(file);
        validateAllowedFileExtension(file);
    }

    private static void validateFilePresence(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new NotFoundFileException();
        }
    }

    private static void validateAllowedFileExtension(MultipartFile file) {
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new InvalidFileExtensionException();
        }
    }

    private static String getFileExtension(String originalFilename) {
        int extensionIndex = originalFilename.lastIndexOf(".");
        if (extensionIndex == -1 || extensionIndex == originalFilename.length() - 1) {
            throw new InvalidFileExtensionException();
        }
        return originalFilename.substring(extensionIndex + 1).toLowerCase();
    }

}