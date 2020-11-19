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
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.swing.plaf.nimbus.State;
import java.util.stream.Collectors;

@Component
@Order(2)
public class InitData implements CommandLineRunner {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    PointOwnerRepository pointOwnerRepository;

    @Autowired
    OrderStateRepository orderStateRepository;

    @Value("${spring.profiles.active}")
    String profile;

    @Override
    public void run(String... args) {

        var defaultStates = DefaultStates.DEFAULT_STATES.stream()
                .map(name -> OrderState.builder()
                        .name(name).build()
                ).collect(Collectors.toList());
        orderStateRepository.saveAll(defaultStates);

        var pointOwner = pointOwnerRepository.save(PointOwner.builder()
                        .emial("covid19@gmail.com")
                        .password("{noop}covid19")
                        .build());

        if (profile.equals("dev") || profile.equals("devauth")) {
            var point = pointRepository.findById(2L)
                    .orElseThrow(
                            () -> new EntityDoesNotExistsException("Point with id = 2 does not exist")
                    );

            pointOwner.setPoint(point);
            point.getPointOwnerList().add(pointOwner);
            pointOwnerRepository.save(pointOwner);
        }
    }
}
