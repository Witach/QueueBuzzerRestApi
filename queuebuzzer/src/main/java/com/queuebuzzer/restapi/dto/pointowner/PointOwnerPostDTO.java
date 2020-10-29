package com.queuebuzzer.restapi.dto.pointowner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointOwnerPostDTO {
    String emial;
    String password;
    Long pointId;
}
