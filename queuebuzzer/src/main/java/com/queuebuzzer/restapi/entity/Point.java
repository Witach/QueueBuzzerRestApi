package com.queuebuzzer.restapi.entity;

import io.swagger.v3.oas.models.links.Link;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedList;
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
    List<OrderState> orderStateList = new LinkedList<>();

    @OneToMany(mappedBy = "point")
    List<Product> productList;

}
