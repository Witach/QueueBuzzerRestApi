package com.queuebuzzer.restapi.controller;

import com.queuebuzzer.restapi.dto.pointowner.PointOwnerDTO;
import com.queuebuzzer.restapi.dto.pointowner.PointOwnerPostDTO;
import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.PointOwner;
import com.queuebuzzer.restapi.service.PointOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController("point-owner")
@RequestMapping("/point-owner")
public class PointOwnerController {

    @Autowired
    PointOwnerService service;

    @ResponseStatus(OK)
    @GetMapping("/{email}")
    public PointOwnerDTO get(@PathVariable String email) {
        return service.getEntityByEmail(email);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<PointOwnerDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@RequestBody PointOwnerPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public PointOwnerDTO post(@RequestBody PointOwnerPostDTO dto) {
        return service.addEntity(dto);
    }
}
