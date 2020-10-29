package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumer.ConsumerPostDTO;
import com.queuebuzzer.restapi.dto.pointowner.PointOwnerDTO;
import com.queuebuzzer.restapi.dto.pointowner.PointOwnerPostDTO;
import com.queuebuzzer.restapi.entity.PointOwner;
import com.queuebuzzer.restapi.repository.PointOwnerRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointOwnerService {
    @Autowired
    PointOwnerRepository repository;

    @Autowired
    EntityMapper entityMapper;

    static String EXCPETION_PATTERN_STRING = "PointOwner with id = %s does not exist";

    public PointOwnerDTO getEntityById(Long id) {
        var pointOwner = loadEntity(id);
        return entityMapper.convertIntoPointOwnerDTO(pointOwner);
    }

    public List<PointOwnerDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertIntoPointOwnerDTO)
                .collect(Collectors.toList());
    }

    public void updateEntity(PointOwnerPostDTO dto, Long id) {
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public PointOwnerDTO addEntity(PointOwnerPostDTO dto) {
        var newPointOwner = entityMapper.convertIntoPointOwner(dto);
        var persistedPointOwner = repository.save(newPointOwner);
        return entityMapper.convertIntoPointOwnerDTO(persistedPointOwner);
    }

    public PointOwner loadEntity(Long id) {
         return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }
}
