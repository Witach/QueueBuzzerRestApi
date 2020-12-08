package com.queuebuzzer.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
public class ImageService {

    @Autowired
    ResourceLoader resourceLoader;
    public void saveImage(MultipartFile file, String prefix) {
        try {
            try {
                Files.createDirectories(Paths.get(prefix));
            } catch (FileAlreadyExistsException e){

            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, Paths.get(prefix), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new NullPointerException("Failed to store empty file.");
        }

    }

    public byte[] loadImage(String prefix) throws IOException {
        Path path = Paths.get(prefix);
        return Files.readAllBytes(path);
    }
}
