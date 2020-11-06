package com.queuebuzzer.restapi.controller;

import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderPostDTO;
import com.queuebuzzer.restapi.service.ConsumerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController("consumer-order")
@RequestMapping("/consumer-order")
public class ConsumerOrderController {

    @Autowired
    ConsumerOrderService service;

    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ConsumerOrderDTO get(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<ConsumerOrderDTO> get() {
        return service.getAllEntities();
    }

//    @ResponseStatus(CREATED)
//    @PatchMapping("/{id}")
//    public void update(@RequestBody ConsumerOrderPostDTO dto, @PathVariable Long id) {
//        service.updateEntity(dto, id);
//    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEntity(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public ConsumerOrderDTO post(@RequestBody ConsumerOrderPostDTO dto) {
        return service.addEntity(dto);
    }
}
