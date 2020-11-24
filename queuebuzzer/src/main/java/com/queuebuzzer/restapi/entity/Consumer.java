package com.queuebuzzer.restapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Consumer extends AppUser {

    @Builder
    public Consumer(Long id, String emial, String password, String consumerCode, List<ConsumerOrder> consumerOrders) {
        super(id, emial, password);
        this.consumerCode = consumerCode;
        this.consumerOrders = consumerOrders;
    }

    String consumerCode;

    //revert

    @OneToMany(mappedBy = "consumer")
    List<ConsumerOrder> consumerOrders;

}
