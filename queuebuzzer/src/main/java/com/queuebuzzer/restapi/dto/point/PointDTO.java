package com.queuebuzzer.restapi.dto.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    Long id;
    String name;
    Long avgAwaitTime;
    String colour;
    String logoImg;
}
