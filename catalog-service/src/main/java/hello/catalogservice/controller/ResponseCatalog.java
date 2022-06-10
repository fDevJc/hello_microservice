package hello.catalogservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import hello.catalogservice.service.CatalogDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
    private LocalDateTime createdAt;

    @Builder
    public ResponseCatalog(String productId, String productName, Integer stock, Integer unitPrice, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
    }

    public static ResponseCatalog of(CatalogDto catalogDto) {
        return ResponseCatalog.builder()
                .productId(catalogDto.getProductId())
                .productName(catalogDto.getProductName())
                .stock(catalogDto.getQuantity())
                .unitPrice(catalogDto.getUnitPrice())
                .build();
    }
}
