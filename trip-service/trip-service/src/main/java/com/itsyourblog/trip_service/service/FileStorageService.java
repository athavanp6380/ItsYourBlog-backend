package com.itsyourblog.trip_service.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public Path getUploadPath() throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        return uploadPath;
    }

    public String storeFile(MultipartFile file) throws IOException {

        Path uploadPath = getUploadPath();

        String originalFileName = file.getOriginalFilename();

        String fileName = UUID.randomUUID() + "_" + originalFileName;

        Path targetLocation = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}
