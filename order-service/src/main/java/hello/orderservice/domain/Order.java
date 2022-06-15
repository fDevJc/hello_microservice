package hello.orderservice.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "orders")
@Entity
public class Order {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer unitPrice;
    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String orderId;
}
