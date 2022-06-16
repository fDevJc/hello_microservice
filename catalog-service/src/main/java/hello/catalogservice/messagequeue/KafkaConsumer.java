package hello.catalogservice.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.catalogservice.domain.Catalog;
import hello.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final CatalogRepository catalogRepository;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("KafkaConsumer.updateQty().kafkaMessage={}", kafkaMessage);

        Map<Object, Object> map;
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String productId = (String) map.get("productId");
        Catalog catalog = catalogRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("not found product"));

        catalog.setStock(catalog.getStock() - (Integer) map.get("quantity"));
    }
}
