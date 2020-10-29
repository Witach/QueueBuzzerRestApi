package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<OrderState, Long> {
}
