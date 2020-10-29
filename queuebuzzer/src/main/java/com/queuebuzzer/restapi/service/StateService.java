package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.state.StateDTO;
import com.queuebuzzer.restapi.dto.state.StatePostDTO;
import com.queuebuzzer.restapi.entity.OrderState;
import com.queuebuzzer.restapi.repository.StateRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {

    @Autowired
    StateRepository repository;

    @Autowired
    EntityMapper entityMapper;

    static String EXCPETION_PATTERN_STRING = "State with id = %s does not exist";

    public StateDTO getEntityById(Long id) {
        var State = loadEntity(id);
        return entityMapper.convertStateIntoDTO(State);
    }

    public List<StateDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertStateIntoDTO)
                .collect(Collectors.toList());
    }

    public void updateEntity(StatePostDTO dto, Long id) {
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public StateDTO addEntity(StatePostDTO dto) {
        var newState = entityMapper.convertIntoState(dto);
        var persistedState = repository.save(newState);
        return entityMapper.convertStateIntoDTO(persistedState);
    }

    public OrderState loadEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }
}
