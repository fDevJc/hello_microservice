package hello.orderservice.controller;

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

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in OrderService PORT: %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable Long userId, @RequestBody RequestOrder requestOrder) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseOrder.of(orderService.createOrder(userId, requestOrder.toDto())));
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable Long userId) {
        return ResponseEntity.ok().body(orderService.getOrdersByUserId(userId).stream()
                .map(ResponseOrder::of)
                .collect(Collectors.toList()));
    }
}
