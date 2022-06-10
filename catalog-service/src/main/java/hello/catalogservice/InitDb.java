package hello.catalogservice;

import hello.catalogservice.domain.Catalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        public void dbInit() {
            for (int i = 1; i < 6; i++) {
                Catalog catalog = Catalog.builder()
                        .productId("productId_" + i)
                        .productName("productName_" + i)
                        .quantity(i * 10)
                        .stock(i * 10)
                        .unitPrice(i * 10)
                        .build();
                em.persist(catalog);
            }
        }
    }
}
