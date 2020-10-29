package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
}