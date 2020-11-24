package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumer.ConsumerDTO;
import com.queuebuzzer.restapi.dto.consumer.ConsumerPostDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.repository.ConsumerOrderRepository;
import com.queuebuzzer.restapi.repository.ConsumerRepository;
import com.queuebuzzer.restapi.repository.OrderStateRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsumerService {

    @Autowired
    ConsumerRepository repository;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    ConsumerOrderRepository consumerOrderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OrderStateRepository orderStateRepository;

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
        var consumerLoad = loadEntity(id);

        consumerLoad.setConsumerCode(Objects.requireNonNullElse(dto.getConsumerCode(),consumerLoad.getConsumerCode()));

        repository.save(consumerLoad);
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public ConsumerDTO addEntity(ConsumerPostDTO dto) {
        var newConsumer = entityMapper.convertIntoConsumer(dto);
        Optional.ofNullable(dto.getPassword()).ifPresent((x) -> newConsumer.setPassword(passwordEncoder.encode(newConsumer.getPassword())));
        var persistedConsumer = repository.save(newConsumer);
        return entityMapper.convertConsumerIntoDTO(persistedConsumer);
    }

    public Consumer loadEntity(Long id) {
       return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }

    public List<ConsumerOrderDTO> getConsumerOrderList(Long id) {
        var activeState = orderStateRepository.getActiveState()
                .orElseThrow();
        return consumerOrderRepository.findAllByConsumer_IdAndOrderStateEquals(id, activeState).stream()
                .map(entityMapper::convertConsumerOrderIntoDTO)
                .collect(Collectors.toList());
    }
}
