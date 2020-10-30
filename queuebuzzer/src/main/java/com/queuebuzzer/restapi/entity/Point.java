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
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    byte[] img;
    byte[] logoImg;
    Long avgAwaitTime;
    String colour;

    //revert

    @OneToMany(mappedBy = "point")
    List<ConsumerOrder> consumerOrderList;

    @OneToMany(mappedBy = "point", fetch = FetchType.EAGER)
    List<PointOwner> pointOwnerList;

    @OneToMany(mappedBy = "point")
    List<OrderState> orderStateList;

    @OneToMany(mappedBy = "point")
    List<Product> productList;

}
