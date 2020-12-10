package com.queuebuzzer.restapi.dto.point;

import com.queuebuzzer.restapi.dto.state.StatePostDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointPostDTO {
    String name;
    Long avgAwaitTime;
    String colour;
    List<StatePostDTO> stateList;
    String logoImg;
}
