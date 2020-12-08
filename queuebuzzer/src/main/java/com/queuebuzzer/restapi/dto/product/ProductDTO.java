package com.queuebuzzer.restapi.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    Long id;
    String name;
    Double price;
    String category;
    Boolean avaliability;
    String img;
    Long point;
    String description;
}
