package com.queuebuzzer.restapi.controller;

import com.queuebuzzer.restapi.dto.product.ProductDTO;
import com.queuebuzzer.restapi.dto.product.ProductPostDTO;
import com.queuebuzzer.restapi.service.ImageService;
import com.queuebuzzer.restapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController("product")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService service;

    @Autowired
    ImageService imageService;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<ProductDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@RequestBody ProductPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public ProductDTO post(@RequestBody ProductPostDTO dto) {
        return service.addEntity(dto);
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}/image")
    public  byte[] getImage(@PathVariable Long id, HttpServletRequest request) throws IOException {
        return imageService.loadImage(id, request.getRealPath("/") + "/product");
    }

    @ResponseStatus(OK)
    @PostMapping("/{id}/image")
    public void  saveImage(@PathVariable Long id, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        imageService.saveImage(file, "product-" + id + ".jpg");
    }

}
