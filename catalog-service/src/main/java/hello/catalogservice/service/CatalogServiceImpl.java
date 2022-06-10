package hello.catalogservice.service;

import hello.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService{
    private final CatalogRepository catalogRepository;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in CatalogService PORT: %s", env.getProperty("local.server.port"));
    }

    @Override
    public List<CatalogDto> getCatalogs() {
        return catalogRepository.findAll().stream()
                .map(CatalogDto::toEntity)
                .collect(Collectors.toList());
    }
}
