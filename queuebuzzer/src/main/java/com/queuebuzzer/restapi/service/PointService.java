package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.point.PointDTO;
import com.queuebuzzer.restapi.dto.point.PointPostDTO;
import com.queuebuzzer.restapi.dto.product.ProductPostDTO;
import com.queuebuzzer.restapi.entity.Point;
import com.queuebuzzer.restapi.repository.PointRepository;
import com.queuebuzzer.restapi.repository.ProductRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointService {

    @Autowired
    PointRepository repository;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    ProductRepository productRepository;

    static String EXCPETION_PATTERN_STRING = "Point with id = %s does not exist";

    public PointDTO getEntityById(Long id) {
        var Point = loadEntity(id);
        return entityMapper.convertPointIntoDTO(Point);
    }

    public List<PointDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertPointIntoDTO)
                .collect(Collectors.toList());
    }

    public void updateEntity(PointPostDTO dto, Long id) {
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public PointDTO addEntity(PointPostDTO dto) {
        var newPoint = entityMapper.convertIntoPoint(dto);
        var persistedPoint = repository.save(newPoint);
        return entityMapper.convertPointIntoDTO(persistedPoint);
    }

    public void createMenu(List<ProductPostDTO> productPostDTOS, Long id) {
        var listOfProducts = productPostDTOS.stream()
                .map(entityMapper::convertIntoProduct)
                .collect(Collectors.toList());
        listOfProducts = productRepository.saveAll(listOfProducts);
        var point = loadEntity(id);
        listOfProducts.forEach(point.getProductList()::add);
        listOfProducts.forEach(product -> product.setPoint(point));
        repository.save(point);
    }

    public Point loadEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }
}
