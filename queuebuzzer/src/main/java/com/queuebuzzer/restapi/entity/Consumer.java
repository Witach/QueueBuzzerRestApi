package com.queuebuzzer.restapi.entity;

import lombok.*;

import javax.persistence.*;

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

    @OneToOne(mappedBy = "consumer")
    ConsumerOrder consumerOrder;

}
