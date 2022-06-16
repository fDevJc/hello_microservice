package hello.catalogservice.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Catalog {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 120, unique = true)
    private String productId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Builder
    public Catalog(String productId, String productName, Integer quantity, Integer stock, Integer unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }
}
