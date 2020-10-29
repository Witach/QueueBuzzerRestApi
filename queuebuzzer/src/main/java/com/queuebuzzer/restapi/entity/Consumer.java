package com.queuebuzzer.restapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Consumer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    String consumerCode;

    //revert

    @OneToMany(mappedBy = "consumer")
    List<ConsumerOrder> consumerOrders;

}
