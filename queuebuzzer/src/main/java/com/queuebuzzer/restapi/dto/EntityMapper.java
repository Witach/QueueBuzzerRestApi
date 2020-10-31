package com.queuebuzzer.restapi.dto;

import com.queuebuzzer.restapi.dto.consumer.ConsumerDTO;
import com.queuebuzzer.restapi.dto.consumer.ConsumerPostDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderPostDTO;
import com.queuebuzzer.restapi.dto.point.PointDTO;
import com.queuebuzzer.restapi.dto.point.PointPostDTO;
import com.queuebuzzer.restapi.dto.point.StatePointDTO;
import com.queuebuzzer.restapi.dto.pointowner.PointOwnerDTO;
import com.queuebuzzer.restapi.dto.pointowner.PointOwnerPostDTO;
import com.queuebuzzer.restapi.dto.product.ProductDTO;
import com.queuebuzzer.restapi.dto.product.ProductPostDTO;
import com.queuebuzzer.restapi.dto.state.StateDTO;
import com.queuebuzzer.restapi.dto.state.StatePostDTO;
import com.queuebuzzer.restapi.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EntityMapper {

    ConsumerDTO convertConsumerIntoDTO(Consumer consumer);
    Consumer convertIntoConsumer(ConsumerPostDTO consumerPostDTO);

    @Mapping(source = "point.id", target = "point")
    ProductDTO convertProductIntoDTO(Product product);
    Product convertIntoProduct(ProductPostDTO productPostDTO);

    PointDTO convertPointIntoDTO(Point point);
    Point convertIntoPoint(PointPostDTO consumerPostDTO);

    @Mapping(source = "point.id", target = "point")
    StateDTO convertStateIntoDTO(OrderState OrderState);
    @Mapping(target = "point.id", source = "pointId")
    OrderState convertIntoState(StatePostDTO statePostDTO);

    OrderState convertFromStatePointDTOIntoState(StatePointDTO statePostDTO);

    @Mappings({
            @Mapping(target = "consumerId", source = "consumer.id"),
            @Mapping(target = "pointId", source = "point.id")
    })
    ConsumerOrderDTO convertConsumerOrderIntoDTO(ConsumerOrder consumerOrder);
    ConsumerOrder convertIntoConsumerOrder(ConsumerOrderPostDTO consumerOrderPostDTO);


    @Mappings({
            @Mapping(target = "id", source = "point.id"),
    })
    PointOwnerDTO convertIntoPointOwnerDTO(PointOwner pointOwner);

    PointOwner convertIntoPointOwner(PointOwnerPostDTO pointOwnerPostDTO);

    default String mapState(OrderState orderState) {
        return orderState.getName();
    }

    default OrderState mapState(String state) {
        return OrderState.builder().name(state).build();
    }
}
