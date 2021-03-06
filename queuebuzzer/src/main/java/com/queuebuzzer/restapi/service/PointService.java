package com.queuebuzzer.restapi.service;

import com.queuebuzzer.restapi.dto.EntityMapper;
import com.queuebuzzer.restapi.dto.consumerorder.ConsumerOrderDTO;
import com.queuebuzzer.restapi.dto.point.PointDTO;
import com.queuebuzzer.restapi.dto.point.PointPostDTO;
import com.queuebuzzer.restapi.dto.point.PointStats;
import com.queuebuzzer.restapi.dto.product.ProductPostDTO;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import com.queuebuzzer.restapi.entity.OrderState;
import com.queuebuzzer.restapi.entity.Point;
import com.queuebuzzer.restapi.repository.ConsumerOrderRepository;
import com.queuebuzzer.restapi.repository.OrderStateRepository;
import com.queuebuzzer.restapi.repository.PointRepository;
import com.queuebuzzer.restapi.repository.ProductRepository;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;

@Service
public class PointService {

    @Autowired
    PointRepository repository;

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    OrderStateRepository orderStateRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ConsumerOrderRepository consumerOrderRepository;

    static String EXCPETION_PATTERN_STRING = "Point with id = %s does not exist";

    public PointDTO getEntityById(Long id) {
        var point = loadEntity(id);
        return convertIntoDTOWithDelay(point);
    }

    private PointDTO convertIntoDTOWithDelay(Point point) {
        var pointStats = getTimeOfDelay(point.getId());
        var dto = entityMapper.convertPointIntoDTO(point);
        dto.setDelay((long)Math.ceil(pointStats.getDelay()));
        return dto;
    }

    public List<PointDTO> getAllEntities() {
        return repository.findAll().stream()
                .map(this::convertIntoDTOWithDelay)
                .collect(Collectors.toList());
    }

    public List<ConsumerOrderDTO> getOrders(Long id, List<String> state) {
        var orderStates = orderStateRepository.findAllByName(id, state);
        if (orderStates.isEmpty()){
            orderStates = orderStateRepository.findAllByPointId(id);
        }
        var pointOrders = consumerOrderRepository.findByPointIdAndOrderStateIn(id, orderStates);
        return pointOrders.stream().map(
                entityMapper::convertConsumerOrderIntoDTO
        ).collect(Collectors.toList());
    }

    public void updateEntity(PointPostDTO dto, Long id) {
        var loadedPoint = loadEntity(id);

        loadedPoint.setColour( Objects.requireNonNullElse(dto.getColour(),loadedPoint.getColour()));
        loadedPoint.setName(Objects.requireNonNullElse(dto.getName(),loadedPoint.getName()));
        loadedPoint.setAvgAwaitTime(Objects.requireNonNullElse(dto.getAvgAwaitTime(),loadedPoint.getAvgAwaitTime()));

        repository.save(loadedPoint);
    }

    public void patchEntity(PointPostDTO dto, Long id) {
        var loadedPoint = loadEntity(id);



        loadedPoint.setColour(requireNonNullElse(dto.getColour(), loadedPoint.getColour()));
        loadedPoint.setName(dto.getName());

        repository.save(loadedPoint);
    }

    public void deleteEntity(Long id) {
        if(!repository.existsById(id))
            throw new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id));
    }

    public PointDTO addEntity(PointPostDTO dto) {
        var newPoint = entityMapper.convertIntoPoint(dto);
        var persistedPoint = repository.save(newPoint);
        var defaultStates = orderStateRepository.getDefaultStates();
        newPoint.setOrderStateList(new LinkedList<>());
        newPoint.getOrderStateList().addAll(defaultStates);
        defaultStates.forEach(defaultState -> defaultState.setPoint(persistedPoint));
        orderStateRepository.saveAll(defaultStates);
        return entityMapper.convertPointIntoDTO(repository.save(newPoint));
    }

    public void createMenu(List<ProductPostDTO> productPostDTOS, Long id) {
        var listOfProducts = productPostDTOS.stream()
                .map(entityMapper::convertIntoProduct)
                .collect(Collectors.toList());
        listOfProducts = productRepository.saveAll(listOfProducts);
        var point = loadEntity(id);
        listOfProducts.forEach(point.getProductList()::add);
        listOfProducts.forEach(product -> product.setPoint(point));
        repository.save(point);
    }

    public Point loadEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(EXCPETION_PATTERN_STRING , id))
                );
    }

    public PointStats getTimeOfDelay(Long pointID) {
        var listOfOrders = repository.findById(pointID)
                .orElseThrow()
                .getConsumerOrderList()
                .stream()
                .filter(consumerOrder -> consumerOrder.getOrderState().getName().equals("DONE") || consumerOrder.getOrderState().getName().equals("READY"))
                .collect(Collectors.toList());

        var countOfOrders = listOfOrders.size();

        var sumOfDelay = listOfOrders.stream()
                .map(this::calculateTimeDelay)
                .reduce(0L, Long::sum);

        return PointStats.builder()
                .delay((double)sumOfDelay / (double)countOfOrders)
                .build();
    }

    private Long calculateTimeDelay(ConsumerOrder element) {
        var startDate = element.getStartOfService();
        var endDate = element.getEndOfService();
        return ChronoUnit.MINUTES.between(startDate, endDate);
    }
}
