package com.queuebuzzer.restapi.dto.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatePostDTO {
    String name;
    Long order;
    Long pointId;
}
