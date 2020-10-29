package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
