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
public class ConsumerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long queueNumber = 0L;

    @ManyToOne()
    Consumer consumer;

    @ManyToOne
    Point point;

    @ManyToOne
    OrderState orderState;

    @ManyToMany
    List<Product> productList;

    String fireBaseToken;
}
