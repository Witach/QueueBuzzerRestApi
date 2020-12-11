package com.queuebuzzer.restapi.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderPostDTO;
import com.queuebuzzer.restapi.dto.consumerorder.EditConsumerOrder;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.entity.Product;
import com.queuebuzzer.restapi.repository.*;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConsumerOrderService {

    @Autowired
    ConsumerOrderRepository repository;

    static String EXCPETION_PATTERN_STRING = "Consumer with id = %s does not exists";

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    OrderStateRepository orderStateRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ConsumerRepository consumerRepository;

    @Autowired
    OrderStateRepository stateRepository;
    @Autowired
    ConsumerOrderRepository orderRepository;

    @Autowired
    NotificationService notificationService;

    public ConsumerOrderDTO getEntityById(Long id) {
        var consumer = loadEntity(id);
        return entityMapper.convertConsumerOrderIntoDTO(consumer);
    }

    public List<ConsumerOrderDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(entityMapper::convertConsumerOrderIntoDTO)
                .collect(Collectors.toList());
    }

    public ConsumerOrder loadEntity(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }
    public void updateEntity(EditConsumerOrder dto, Long id, String url) throws FirebaseMessagingException {
        if(dto != null){
            var order = loadEntity(id);
            var newState = stateRepository.findByNameAndPointId(dto.getStateName(), order.getPoint().getId())
                    .orElseThrow(() -> new EntityDoesNotExistsException(String.format(PointService.EXCPETION_PATTERN_STRING, dto.getStateName())));
            var oldState = order.getOrderState();

            oldState.getConsumerOrderList().remove(order);
            newState.getConsumerOrderList().add(order);
            order.setOrderState(newState);
            if(newState.getName().equals("READY") && Objects.nonNull(order.getFireBaseToken())) {
                notificationService.notifyUserAboutOrder(order, url);
            }
            orderRepository.save(order);
            stateRepository.save(oldState);
            stateRepository.save(newState);
        }
    }


    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
       repository.deleteById(id);
    }

    public ConsumerOrderDTO addEntity(ConsumerOrderPostDTO dto) {
        var newConsumerOrder = entityMapper.convertIntoConsumerOrder(dto);
        var point = pointRepository.findById(dto.getPointId()).orElseThrow(EntityExistsException::new);
        var consumer = consumerRepository.findById(dto.getConsumerId()).orElseThrow(EntityExistsException::new);
        var products = getProductsOfList(dto.getProductsIds());
        var activeState = orderStateRepository.findByNameAndPointId("ACCEPTED", point.getId())
                .orElseThrow(
                        EntityExistsException::new
                );


        incrementQueueNumber(newConsumerOrder, point);
        newConsumerOrder.setOrderState(orderStateRepository.findByNameAndPointId("ACCEPTED", point.getId()).orElseThrow(EntityExistsException::new));
        newConsumerOrder.setProductList(products);
        newConsumerOrder.setPoint(point);
        newConsumerOrder.setConsumer(consumer);
        newConsumerOrder.setOrderState(activeState);

        var persistedConsumerOrder = repository.save(newConsumerOrder);
        pointRepository.save(point);
        return entityMapper.convertConsumerOrderIntoDTO(persistedConsumerOrder);
    }

    private List<Product> getProductsOfList(List<Long> productsIds) {
        return productRepository.findAllById(productsIds);
    }

    private void incrementQueueNumber(ConsumerOrder newConsumerOrder, com.queuebuzzer.restapi.entity.Point point) {
        var currenctQueueNumber = point.getCurrentMaxQueueNumber();
        var maxNumber = point.getMaxQueueNumber();
        if(currenctQueueNumber + 1 >= maxNumber ){
            point.setCurrentMaxQueueNumber(0L);
            newConsumerOrder.setQueueNumber(0L);
        } else {
            point.setCurrentMaxQueueNumber(currenctQueueNumber + 1);
            newConsumerOrder.setQueueNumber(currenctQueueNumber + 1);
        }
    }
}
