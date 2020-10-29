package com.queuebuzzer.restapi.bootstrap;

import com.queuebuzzer.restapi.entity.Point;
import com.queuebuzzer.restapi.entity.PointOwner;
import com.queuebuzzer.restapi.repository.PointOwnerRepository;
import com.queuebuzzer.restapi.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    PointOwnerRepository pointOwnerRepository;

    @Override
    public void run(String... args) throws Exception {

        var point = pointRepository.save(
                Point.builder()
                .avgAwaitTime(20_000L)
                .colour("#FFFFFF")
                .name("Zahir")
                .build()
        );


        var pointOwner = PointOwner.builder()
                        .emial("Witaszek98@wp.pl")
                        .password("{noop}rrwertwer")
                        .point(point)
                        .build();
        point.setPointOwnerList(List.of(pointOwner));
        pointOwnerRepository.save(pointOwner);
    }
}
