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
public class OrderState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @ManyToOne
    Point point;

    //revert

    @OneToMany(mappedBy = "orderState")
    List<ConsumerOrder> consumerOrderList;
}
