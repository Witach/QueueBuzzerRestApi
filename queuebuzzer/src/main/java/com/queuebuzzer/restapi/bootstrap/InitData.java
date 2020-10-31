package com.queuebuzzer.restapi.bootstrap;

import com.queuebuzzer.restapi.entity.Point;
import com.queuebuzzer.restapi.entity.PointOwner;
import com.queuebuzzer.restapi.repository.PointOwnerRepository;
import com.queuebuzzer.restapi.repository.PointRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class InitData implements CommandLineRunner {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    PointOwnerRepository pointOwnerRepository;

    @Override
    public void run(String... args) {
        var pointOwner = pointOwnerRepository.save(PointOwner.builder()
                        .emial("covid19@gmail.com")
                        .password("{noop}covid19")
                        .build());

        var point = pointRepository.findById(2L)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException("Point with id = 2 does not exist")
                );

        pointOwner.setPoint(point);
        point.getPointOwnerList().add(pointOwner);
        pointOwnerRepository.save(pointOwner);
    }
}
