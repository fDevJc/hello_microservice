package hello.catalogservice.controller;

import hello.catalogservice.service.CatalogDto;
import hello.catalogservice.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/catalog-service")
@RestController
public class CatalogController {
    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in CatalogService PORT: %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        return ResponseEntity.ok().body(catalogService.getCatalogs().stream()
                .map(ResponseCatalog::of)
                .collect(Collectors.toList()));
    }
}
