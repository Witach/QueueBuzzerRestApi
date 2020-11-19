package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
}
