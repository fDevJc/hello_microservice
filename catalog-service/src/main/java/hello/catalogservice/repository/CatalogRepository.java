package hello.catalogservice.repository;

import hello.catalogservice.domain.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

}
