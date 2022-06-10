package hello.catalogservice.service;

import hello.catalogservice.domain.Catalog;
import lombok.Builder;
import lombok.Data;

@Data
public class CatalogDto {
    private String productId;
    private String productName;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    private String orderId;
    private String userId;

    @Builder
    public CatalogDto(String productId, String productName, Integer quantity, Integer unitPrice, Integer totalPrice, String orderId, String userId) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.userId = userId;
    }

    public static CatalogDto toEntity(Catalog catalog) {
        return CatalogDto.builder()
                .productId(catalog.getProductId())
                .productName(catalog.getProductName())
                .quantity(catalog.getQuantity())
                .unitPrice(catalog.getUnitPrice())
                .build();
    }
}
