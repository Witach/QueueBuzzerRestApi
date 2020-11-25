package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    @Query("from OrderState o where o.name = 'ACCEPTED'")
    Optional<OrderState> getStartState();

    @Query("from OrderState o where o.name in ('ACCEPTED', 'IN_PROGRESS', 'READY', 'DONE')")
    List<OrderState> getDefaultStates();

    Optional<OrderState> findByName(String name);
    @Query("from OrderState o where o.point.id = :id and  o.name in :names")
    List<OrderState> findAllByName(Long id, List<String> names);

    List<OrderState> findAllByPointId(Long id);
    Optional<OrderState> findByPointIdAndName(Long pointId, String name);
}
