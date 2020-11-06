package com.queuebuzzer.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {

    @Autowired
    ResourceLoader resourceLoader;
    public void saveImage(MultipartFile file, String prefix) {
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(prefix), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new NullPointerException("Failed to store empty file.");
        }

    }

    public byte[] loadImage(Long entityId, String prefix) throws IOException {
        Path path = Paths.get(prefix + "/" + entityId);
        return Files.readAllBytes(path);
    }
}
