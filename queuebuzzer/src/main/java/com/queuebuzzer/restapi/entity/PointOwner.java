package com.queuebuzzer.restapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String emial;
    String password;

    @ManyToOne
    Point point;
}
