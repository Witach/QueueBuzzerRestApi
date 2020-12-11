package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PointImageService {

    @Autowired
    ImageService imageService;

    @Autowired
    PointRepository pointRepository;

    public void saveProductImage(MultipartFile file, Long id, String url) {
        var path = getPathOfPoint(id);
        var point = pointRepository.findById(id).orElseThrow();
        point.setLogoImg(url);
        imageService.saveImage(file, path);
        pointRepository.save(point);
    }

    public byte[] loadProductImage(Long id) throws IOException {
        return imageService.loadImage(getPathOfPoint(id));
    }

    public String getPathOfPoint(Long id) {
        var point = pointRepository.findById(id).orElseThrow();
        return "points-info/" + point.getId() + "/logo.jpg";
    }
}
