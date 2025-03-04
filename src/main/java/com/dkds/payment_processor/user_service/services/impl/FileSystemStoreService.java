package com.dkds.payment_processor.user_service.services.impl;

import com.dkds.payment_processor.user_service.dto.FileDto;
import com.dkds.payment_processor.user_service.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class FileSystemStoreService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStoreService(@Value("${store-service.save-location}") String saveLocation) {
        if (saveLocation == null || saveLocation.trim().isEmpty()) {
            throw new RuntimeException("File upload location can not be Empty.");
        }
        this.rootLocation = Paths.get(saveLocation);
    }

    @Override
    public List<FileDto> store(MultipartFile[] files) {
        List<FileDto> fileList = new LinkedList<>();
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new RuntimeException("Failed to store empty file.");
                }
                String originalName = file.getOriginalFilename();
                String savedFileName = UUID.randomUUID().toString();
                Path destinationFile = this.rootLocation.resolve(Paths.get(savedFileName)).normalize().toAbsolutePath();
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                }
                fileList.add(FileDto.builder().fileName(originalName).savedName(savedFileName).fieldName(file.getName()).build());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
        return fileList;
    }
}
