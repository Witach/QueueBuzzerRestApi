package com.queuebuzzer.restapi.dto.consumerorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditConsumerOrder {
    String stateName;
}
