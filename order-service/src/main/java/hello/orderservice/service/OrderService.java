package hello.orderservice.service;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(Long userId, OrderDto orderDto);

    List<OrderDto> getOrdersByUserId(Long userId);
}
