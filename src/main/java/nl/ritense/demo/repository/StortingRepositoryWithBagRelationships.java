package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Storting;
import org.springframework.data.domain.Page;

public interface StortingRepositoryWithBagRelationships {
    Optional<Storting> fetchBagRelationships(Optional<Storting> storting);

    List<Storting> fetchBagRelationships(List<Storting> stortings);

    Page<Storting> fetchBagRelationships(Page<Storting> stortings);
}
