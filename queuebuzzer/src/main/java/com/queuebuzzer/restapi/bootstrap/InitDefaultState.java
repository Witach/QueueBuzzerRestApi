package com.queuebuzzer.restapi.bootstrap;

import com.queuebuzzer.restapi.entity.OrderState;
import com.queuebuzzer.restapi.entity.PointOwner;
import com.queuebuzzer.restapi.repository.OrderStateRepository;
import com.queuebuzzer.restapi.repository.PointOwnerRepository;
import com.queuebuzzer.restapi.repository.PointRepository;
import com.queuebuzzer.restapi.utils.DefaultStates;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Order(1)
public class InitDefaultState implements CommandLineRunner {

    @Autowired
    OrderStateRepository orderStateRepository;

    @Override
    public void run(String... args) {
        var defaultStates = DefaultStates.DEFAULT_STATES.stream()
                .map(name -> OrderState.builder()
                        .name(name).build()
                ).collect(Collectors.toList());
        orderStateRepository.saveAll(defaultStates);
    }
}
