package com.queuebuzzer.restapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPostDTO {
    String name;
    Double price;
    String category;
    Boolean avaliability;
}
