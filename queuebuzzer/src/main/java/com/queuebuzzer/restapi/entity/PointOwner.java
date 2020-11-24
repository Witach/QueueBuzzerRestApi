package com.queuebuzzer.restapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointOwner extends AppUser {

    @Builder
    public PointOwner(Long id, String emial, String password, Point point) {
        super(id, emial, password);
        this.point = point;
    }

    @ManyToOne
    Point point;
}
