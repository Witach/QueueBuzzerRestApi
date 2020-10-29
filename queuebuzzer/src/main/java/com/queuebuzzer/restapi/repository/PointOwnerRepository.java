package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.entity.PointOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointOwnerRepository extends JpaRepository<PointOwner, Long> {
    Optional<PointOwner> findByEmial(String emial);
}
