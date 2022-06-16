package hello.orderservice.messagequeue;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private int unitPrice;
    private int totalPrice;
}
