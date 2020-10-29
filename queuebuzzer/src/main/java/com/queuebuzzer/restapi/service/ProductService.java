package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.product.ProductDTO;
import com.queuebuzzer.restapi.dto.product.ProductPostDTO;
import com.queuebuzzer.restapi.entity.Product;
import com.queuebuzzer.restapi.repository.ProductRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Autowired
    EntityMapper entityMapper;

    static String EXCPETION_PATTERN_STRING = "Product with id = %s does not exist";

    public ProductDTO getEntityById(Long id) {
        var Product = loadEntity(id);
        return entityMapper.convertProductIntoDTO(Product);
    }

    public List<ProductDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertProductIntoDTO)
                .collect(Collectors.toList());
    }

    public void updateEntity(ProductPostDTO dto, Long id) {
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public ProductDTO addEntity(ProductPostDTO dto) {
        var newProduct = entityMapper.convertIntoProduct(dto);
        var persistedProduct = repository.save(newProduct);
        return entityMapper.convertProductIntoDTO(persistedProduct);
    }

    public Product loadEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }
}
