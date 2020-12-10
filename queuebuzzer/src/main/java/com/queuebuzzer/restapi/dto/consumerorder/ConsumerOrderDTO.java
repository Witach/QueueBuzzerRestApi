package com.queuebuzzer.restapi.dto.consumerorder;

import com.queuebuzzer.restapi.dto.product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerOrderDTO {
    Long id;
    Long consumerId;
    Long pointId;
    String stateName;
    Long queueNumber;
    List<ProductDTO> productList;
}
