package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ConsumerImageService {

    @Autowired
    ImageService imageService;

    @Autowired
    ProductRepository productRepository;

    public void saveProductImage(MultipartFile file, Long id, String url) {
        var path = getPathOfProuct(id);
        var product = productRepository.findById(id).orElseThrow();
        product.setImg(url);
        imageService.saveImage(file, path);
        productRepository.save(product);
    }

    public byte[] loadProductImage(Long id) throws IOException {
        return imageService.loadImage(getPathOfProuct(id));
    }

    public String getPathOfProuct(Long id) {
        var product = productRepository.findById(id).orElseThrow();
        var point =  product.getPoint();
        return "points-info/" + point.getId() + "/product-" + product.getId() + ".jpg";
    }
}
