package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Stremming;
import org.springframework.data.domain.Page;

public interface StremmingRepositoryWithBagRelationships {
    Optional<Stremming> fetchBagRelationships(Optional<Stremming> stremming);

    List<Stremming> fetchBagRelationships(List<Stremming> stremmings);

    Page<Stremming> fetchBagRelationships(Page<Stremming> stremmings);
}
