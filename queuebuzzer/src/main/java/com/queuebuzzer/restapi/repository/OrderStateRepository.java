package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    @Query("from OrderState o where o.name in ('ACTIVE', 'DONE')")
   List<OrderState> getDefaultStates();

    @Query("from OrderState o where o.name in ('ACTIVE')")
    Optional<OrderState> getActiveState();
}
