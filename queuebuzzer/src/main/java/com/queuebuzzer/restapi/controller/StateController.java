package com.queuebuzzer.restapi.controller;

import com.queuebuzzer.restapi.dto.state.StateDTO;
import com.queuebuzzer.restapi.dto.state.StatePostDTO;
import com.queuebuzzer.restapi.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController("state")
@RequestMapping("/state")
public class StateController {

    @Autowired
    StateService service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public StateDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<StateDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@RequestBody StatePostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public StateDTO post(@RequestBody StatePostDTO dto) {
        return service.addEntity(dto);
    }
}
