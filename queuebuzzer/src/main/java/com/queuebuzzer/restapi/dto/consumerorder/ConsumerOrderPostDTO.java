package com.queuebuzzer.restapi.dto.consumerorder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerOrderPostDTO {
    String stateName;
    Long consumerId;
    Long pointId;
    List<Long> productsIds;
    String fireBaseToken;
}
