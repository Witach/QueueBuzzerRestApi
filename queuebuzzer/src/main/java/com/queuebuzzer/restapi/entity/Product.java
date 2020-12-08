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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Double price;
    String img;

    String category;
    Boolean avaliability;

    @ManyToOne
    Point point;

    @ManyToMany(mappedBy = "productList")
    List<ConsumerOrder> consumerOrderList;

    String description;
}
