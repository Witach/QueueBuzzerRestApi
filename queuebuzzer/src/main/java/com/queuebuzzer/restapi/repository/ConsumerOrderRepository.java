package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsumerOrderRepository extends JpaRepository<ConsumerOrder, Long> {
    @Query("from ConsumerOrder c where c.consumer.id = :consumer_id and c.orderState.name in :orderStates")
    List<ConsumerOrder> findAllByConsumer_IdAndOrOrderStateIn(Long consumer_id, List<String> orderStates);

    List<ConsumerOrder> findByPointIdAndOrderStateIn(Long pointId, List<OrderState> orderStates);
}
