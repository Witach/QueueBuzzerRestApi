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
    Long consumerId;
    Long pointId;
    String state;
    List<ProductDTO> productList;
}
