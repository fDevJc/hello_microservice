package hello.orderservice.service;

import hello.orderservice.domain.Order;
import lombok.Data;

@Data
public class OrderDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    public static OrderDto of(Order order) {
        return new OrderDto();
    }

    public Order toEntity() {
        return new Order();
    }
}
