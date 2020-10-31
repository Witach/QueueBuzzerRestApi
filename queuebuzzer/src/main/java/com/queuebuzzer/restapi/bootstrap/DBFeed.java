package com.queuebuzzer.restapi.bootstrap;

import com.github.javafaker.Faker;
import com.queuebuzzer.restapi.entity.*;
import com.queuebuzzer.restapi.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.queuebuzzer.restapi.utils.ListUtils.randomChoice;

@Component
@Order(1)
@Profile({"dev", "devauth"})
public class DBFeed implements CommandLineRunner {

    PointRepository pointRepository;
    PointOwnerRepository pointOwnerRepository;
    ConsumerRepository consumerRepository;
    ConsumerOrderRepository consumerOrderRepository;
    StateRepository stateRepository;
    ProductRepository productRepository;
    Faker faker;

    @Value("${fake-generator.number-of-points}")
    int numberOfPoints;
    @Value("${fake-generator.enabled}")
    boolean enabled;

    List<String> fakeColours = List.of("#ffffff", "#000000", "#ff0000", "#00ff00", "#0000ff", "#0004ff");

    public DBFeed(PointRepository pointRepository, PointOwnerRepository pointOwnerRepository, ConsumerRepository consumerRepository, ConsumerOrderRepository consumerOrderRepository, StateRepository stateRepository, ProductRepository productRepository, Faker faker) {
        this.pointRepository = pointRepository;
        this.pointOwnerRepository = pointOwnerRepository;
        this.consumerRepository = consumerRepository;
        this.consumerOrderRepository = consumerOrderRepository;
        this.stateRepository = stateRepository;
        this.productRepository = productRepository;
        this.faker = faker;
    }

    @Override
    public void run(String... args) throws Exception {
        if (enabled) {
            var fakePoints = IntStream.range(0, numberOfPoints)
                    .mapToObj(i -> fakePoint())
                    .collect(Collectors.toList());
        }
    }


     Point fakePoint() {
        var point = Point.builder()
                .colour(getFakeColour())
                .avgAwaitTime(getFakeAwgTime())
                .consumerOrderList(fakeConsumerOrderList())
                .name(faker.pokemon().name())
                .pointOwnerList(fakePointOwnerList())
                .orderStateList(fakeOrderStateList())
                .productList(fakeProductsList())
                .build();
        point.getConsumerOrderList().forEach(pointConsumerOrderBinder(point));
        point.getProductList().forEach(product -> product.setPoint(point));
        point.getPointOwnerList().forEach(pointOwner -> pointOwner.setPoint(point));
        pointRepository.save(point);

        consumerOrderRepository.saveAll(point.getConsumerOrderList());
        pointOwnerRepository.saveAll(point.getPointOwnerList());
        productRepository.saveAll(point.getProductList());
        stateRepository.saveAll(point.getOrderStateList());
        return point;
    }


    private java.util.function.Consumer<ConsumerOrder> pointConsumerOrderBinder(Point point) {
        return consumerOrder -> {

            var products = point.getProductList();
            IntStream.range(0, 3).forEach(i -> {
                var product = randomChoice(products);
                product.getConsumerOrderList().add(consumerOrder);
                consumerOrder.getProductList().add(product);
                productRepository.save(product);
                consumerOrderRepository.save(consumerOrder);
            });

            consumerOrder.setPoint(point);
            var state = randomChoice(point.getOrderStateList());
            consumerOrder.setOrderState(state);
            state.getConsumerOrderList().add(consumerOrder);
        };
    }

    private List<Product> fakeProductsList() {
        return IntStream.range(0, 20)
                .mapToObj(i -> fakeProduct())
                .collect(Collectors.toList());
    }

    private Product fakeProduct() {
        return productRepository.save(Product.builder()
                .avaliability(Math.random() < 0.5)
                .category(faker.animal().name())
                .price(Math.round(Math.random() * 10000.0) / 100.0)
                .name(faker.food().dish())
                .consumerOrderList(new LinkedList<>())
                .build());

    }

    private List<OrderState> fakeOrderStateList() {
        return IntStream.range(0, 5)
                .mapToObj(i -> fakeOrderState())
                .collect(Collectors.toList());
    }

    private OrderState fakeOrderState() {
        return stateRepository.save(OrderState.builder()
                .name(faker.pokemon().name())
                .consumerOrderList(new LinkedList<>())
                .build());
    }

    private List<PointOwner> fakePointOwnerList() {
        return IntStream.range(0, 5)
                .mapToObj(i -> this.fakePointOwner())
                .collect(Collectors.toList());
    }

    private PointOwner fakePointOwner() {
        return pointOwnerRepository.save(PointOwner.builder()
                .emial(faker.bothify("????##@gmail.com"))
                .password("{noop}covid19")
                .build());
    }

    private List<ConsumerOrder> fakeConsumerOrderList() {
        var consumer = fakeConsumer();
        return IntStream.range(0, 20)
                .mapToObj(i -> this.fakeConsumerOrder())
                .map(order -> {
                    order.setConsumer(consumer);
                    return order;
                })
                .collect(Collectors.toList());
    }

    private ConsumerOrder fakeConsumerOrder() {
        return consumerOrderRepository.save(ConsumerOrder.builder()
                .productList(new LinkedList<>())
                .build());
    }

    private Consumer fakeConsumer() {
        return consumerRepository.save(Consumer.builder()
                .consumerCode(faker.regexify("[a-z1-9]{10}"))
                .consumerOrders(new LinkedList<>())
                .build());

    }

    private Long getFakeAwgTime() {
        return (long) (Math.random() * 10_000);
    }

    private String getFakeColour() {
        return randomChoice(fakeColours);
    }
    
}
