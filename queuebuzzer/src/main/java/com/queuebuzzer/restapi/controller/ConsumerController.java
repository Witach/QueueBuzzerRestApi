package com.queuebuzzer.restapi.controller;

import com.queuebuzzer.restapi.dto.consumer.ConsumerDTO;
import com.queuebuzzer.restapi.dto.consumer.ConsumerPostDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.repository.ConsumerRepository;
import com.queuebuzzer.restapi.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController("consumer")
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    ConsumerService service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ConsumerDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<ConsumerDTO> get() {
        return service.getAllEntities();
    }

    @ResponseStatus(CREATED)
    @PatchMapping("/{id}")
    public void update(@RequestBody ConsumerPostDTO dto, @PathVariable Long id) {
        service.updateEntity(dto, id);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public ConsumerDTO post(@RequestBody ConsumerPostDTO dto) {
        return service.addEntity(dto);
    }

    @ResponseStatus(OK)
    @GetMapping("/{id}/orders")
    public List<ConsumerOrderDTO> consumerOrderList(@PathVariable Long id) {
        return service.getConsumerOrderList(id);
    }
 }
