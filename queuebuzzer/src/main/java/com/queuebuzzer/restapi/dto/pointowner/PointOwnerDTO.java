package com.queuebuzzer.restapi.dto.pointowner;

import com.queuebuzzer.restapi.dto.point.PointDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointOwnerDTO {
    Long id;
    String emial;
    PointDTO point;
}
