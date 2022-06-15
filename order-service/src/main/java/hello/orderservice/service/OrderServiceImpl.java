package hello.orderservice.service;

import hello.orderservice.domain.Order;
import hello.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(Long userId, OrderDto orderDto) {
        Order order = orderRepository.save(orderDto.toEntity());
        return OrderDto.of(order);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        System.out.println("userId = " + userId);
        return orderRepository.findByUserId(userId).stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());
    }
}
