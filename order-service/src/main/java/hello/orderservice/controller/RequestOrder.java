package hello.orderservice.controller;

import hello.orderservice.service.OrderDto;
import lombok.Data;

@Data
public class RequestOrder {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;

    public OrderDto toDto() {
        return new OrderDto();
    }
}
