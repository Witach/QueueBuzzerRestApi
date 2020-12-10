package com.queuebuzzer.restapi.controller;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.dto.point.PointDTO;
import com.queuebuzzer.restapi.dto.point.PointPostDTO;
import com.queuebuzzer.restapi.dto.product.ProductDTO;
import com.queuebuzzer.restapi.dto.product.ProductPostDTO;
import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.service.PointImageService;
import com.queuebuzzer.restapi.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController("point")
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService service;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    PointImageService pointImageService;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public PointDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<PointDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}/orders")
    public List<ConsumerOrderDTO> getOrdersOfPoint(@PathVariable Long id, @RequestParam(required = false) List<String> state) {
        return service.getOrders(id, state);
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@RequestBody PointPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public PointDTO post(@RequestBody PointPostDTO dto) {
        return service.addEntity(dto);
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}/products")
    public List<ProductDTO> getProductsOfPoint(@PathVariable Long id) {
        return service.loadEntity(id).getProductList().stream()
                .map(entityMapper::convertProductIntoDTO)
                .collect(Collectors.toList());
    }

    @ResponseStatus(CREATED)
    @PostMapping("/{id}/products")
    public void postProductsOfPoint(@PathVariable Long id, @RequestBody List<ProductPostDTO> productPostDTOS) {
        service.createMenu(productPostDTOS, id);
    }

    @ResponseStatus(OK)
    @GetMapping( value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public  byte[] getImage(@PathVariable Long id) throws IOException {
        return pointImageService.loadProductImage(id);
    }

    @ResponseStatus(OK)
    @PostMapping("/{id}/image")
    public void  saveImage(@PathVariable Long id, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        var url = request.getRequestURL().toString();
        pointImageService.saveProductImage(file, id, url);
    }
}
