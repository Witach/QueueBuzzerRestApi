package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.Consumer;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumerOrderRepository extends JpaRepository<ConsumerOrder, Long> {
    List<ConsumerOrder> findAllByConsumer_IdAndOrderStateEquals(Long consumer_id, OrderState orderState);
}
