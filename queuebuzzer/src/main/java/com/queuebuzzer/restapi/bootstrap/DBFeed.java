package com.queuebuzzer.restapi.bootstrap;

import com.github.javafaker.Faker;
import com.queuebuzzer.restapi.entity.*;
import com.queuebuzzer.restapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.queuebuzzer.restapi.utils.DefaultStates.DEFAULT_STATES;
import static com.queuebuzzer.restapi.utils.ListUtils.randomChoice;

@Component
@Order(2)
@Profile({"dev", "devauth"})
public class DBFeed implements CommandLineRunner {

    PointRepository pointRepository;
    PointOwnerRepository pointOwnerRepository;
    ConsumerRepository consumerRepository;
    ConsumerOrderRepository consumerOrderRepository;
    OrderStateRepository stateRepository;
    ProductRepository productRepository;
    Faker faker;
    List<String> categories;

    @Autowired
    OrderStateRepository orderStateRepository;

    @Value("${fake-generator.number-of-points}")
    int numberOfPoints;
    @Value("${fake-generator.enabled}")
    boolean enabled;

    List<String> fakeColours = List.of("#8F3A84", "#000000", "#a64dff", "#FF6F61", "#ff3300", "#88B04B");

    public DBFeed(PointRepository pointRepository, PointOwnerRepository pointOwnerRepository, ConsumerRepository consumerRepository, ConsumerOrderRepository consumerOrderRepository, OrderStateRepository stateRepository, ProductRepository productRepository, Faker faker) {
        this.pointRepository = pointRepository;
        this.pointOwnerRepository = pointOwnerRepository;
        this.consumerRepository = consumerRepository;
        this.consumerOrderRepository = consumerOrderRepository;
        this.stateRepository = stateRepository;
        this.productRepository = productRepository;
        this.faker = faker;
        this.categories = IntStream.range(0, 10)
                .mapToObj(i -> faker.animal().name())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void run(String... args) throws Exception {
        if (enabled) {
            var fakePoints = IntStream.range(0, numberOfPoints)
                    .mapToObj(i -> fakePoint())
                    .collect(Collectors.toList());
        }
    }


    @Transactional
    public Point fakePoint() {
        var defaultOrderStates = getInstancesOfDefaultStates();

        var point = Point.builder()
                .colour(getFakeColour())
                .avgAwaitTime(getFakeAwgTime())
                .consumerOrderList(fakeConsumerOrderList())
                .name(faker.pokemon().name())
                .pointOwnerList(fakePointOwnerList())
                .logoImg("http://10.0.2.2:8080/logo.png")
                .orderStateList(defaultOrderStates)
                .productList(fakeProductsList())
                .currentMaxQueueNumber(0L)
                .maxQueueNumber(99L)
                .build();

        orderStateRepository.saveAll(defaultOrderStates);
        defaultOrderStates.forEach(orderState -> orderState.setPoint(point));
        point.getConsumerOrderList().forEach(pointConsumerOrderBinder(point));
        point.getProductList().forEach(product -> product.setPoint(point));
        point.getPointOwnerList().forEach(pointOwner -> pointOwner.setPoint(point));
        point.getConsumerOrderList().forEach(order -> defaultOrderStates.get(0));
        pointRepository.save(point);
        consumerOrderRepository.saveAll(point.getConsumerOrderList());
        pointOwnerRepository.saveAll(point.getPointOwnerList());
        productRepository.saveAll(point.getProductList());
        point.getOrderStateList().addAll(defaultOrderStates);
        stateRepository.saveAll(point.getOrderStateList());
        return point;
    }

    private List<OrderState> getInstancesOfDefaultStates() {
        return DEFAULT_STATES.stream().map(name -> OrderState.builder()
        .name(name)
        .build()).collect(Collectors.toList());
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
                .category(randomChoice(categories))
                .price(Math.round(Math.random() * 10000.0) / 100.0)
                .name(faker.food().dish())
                .img("http://10.0.2.2:8080/logo.png")
                .description(faker.lorem().fixedString(64))
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
                .queueNumber(0L)
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
