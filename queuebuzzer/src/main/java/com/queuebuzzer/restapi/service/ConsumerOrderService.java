package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderPostDTO;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.repository.ConsumerOrderRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerOrderService {

    @Autowired
    ConsumerOrderRepository repository;

    static String EXCPETION_PATTERN_STRING = "Consumer with id = %s does not exists";

    @Autowired
    EntityMapper entityMapper;

    public ConsumerOrderDTO getEntityById(Long id) {
        var consumer = loadEntity(id);
        return entityMapper.convertConsumerOrderIntoDTO(consumer);
    }

    public List<ConsumerOrderDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertConsumerOrderIntoDTO)
                .collect(Collectors.toList());
    }

    public ConsumerOrder loadEntity(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }

    public void updateEntity(ConsumerOrderPostDTO dto, Long id) {
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
       repository.deleteById(id);
    }

    public ConsumerOrderDTO addEntity(ConsumerOrderPostDTO dto) {
        var newConsumerOrder = entityMapper.convertIntoConsumerOrder(dto);
        var persistedConsumerOrder = repository.save(newConsumerOrder);
        return entityMapper.convertConsumerOrderIntoDTO(persistedConsumerOrder);
    }
}
