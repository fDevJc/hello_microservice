package hello.orderservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import hello.orderservice.service.OrderDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    private String orderId;

    public static ResponseOrder of(OrderDto orderDto) {
        return new ResponseOrder();
    }
}
