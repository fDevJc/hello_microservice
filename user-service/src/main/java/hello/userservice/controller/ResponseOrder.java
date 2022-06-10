package hello.userservice.controller;

import java.time.LocalDateTime;

public class ResponseOrder {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createAt;

    private String orderId;
}
