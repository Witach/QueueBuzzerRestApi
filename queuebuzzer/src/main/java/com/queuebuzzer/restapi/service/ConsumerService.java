package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumer.ConsumerDTO;
import com.queuebuzzer.restapi.dto.consumer.ConsumerPostDTO;
import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.repository.ConsumerRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerService {

    @Autowired
    ConsumerRepository repository;

    @Autowired
    EntityMapper entityMapper;

    static String EXCPETION_PATTERN_STRING = "PointOwner with id = %s does not exist";

    public ConsumerDTO getEntityById(Long id) {
        var consumer = loadEntity(id);
        return entityMapper.convertConsumerIntoDTO(consumer);
    }

    public List<ConsumerDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertConsumerIntoDTO)
                .collect(Collectors.toList());
    }

    public void updateEntity(ConsumerPostDTO dto, Long id) {
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public ConsumerDTO addEntity(ConsumerPostDTO dto) {
        var newConsumer = entityMapper.convertIntoConsumer(dto);
        var persistedConsumer = repository.save(newConsumer);
        return entityMapper.convertConsumerIntoDTO(persistedConsumer);
    }

    public Consumer loadEntity(Long id) {
       return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }
}
