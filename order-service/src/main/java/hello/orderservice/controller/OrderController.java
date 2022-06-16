package hello.orderservice.controller;

import hello.orderservice.messagequeue.KafkaProducer;
import hello.orderservice.messagequeue.OrderProducer;
import hello.orderservice.service.OrderDto;
import hello.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/order-service")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final Environment env;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in OrderService PORT: %s", env.getProperty("local.server.port"));
    }

//    @PostMapping("/{userId}/orders")
//    public ResponseEntity<ResponseOrder> createOrder(@PathVariable Long userId, @RequestBody RequestOrder requestOrder) {
//        OrderDto orderDto = requestOrder.toDto();
//
//        //use jpa
//        ResponseOrder responseOrder = ResponseOrder.of(orderService.createOrder(userId, orderDto));
//        //send this order to the kafka
//        kafkaProducer.send("example-catalog-topic", orderDto);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
//    }


    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable Long userId, @RequestBody RequestOrder requestOrder) {
        OrderDto orderDto = requestOrder.toDto();

        //use kafka message
        orderDto.setTotalPrice(requestOrder.getQuantity() * requestOrder.getUnitPrice());

        //send this order to the kafka
        orderProducer.send("orders", orderDto);
        kafkaProducer.send("example-catalog-topic", orderDto);

        //sink-connect 추가

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseOrder.of(orderDto););
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable Long userId) {
        return ResponseEntity.ok().body(orderService.getOrdersByUserId(userId).stream()
                .map(ResponseOrder::of)
                .collect(Collectors.toList()));
    }
}
