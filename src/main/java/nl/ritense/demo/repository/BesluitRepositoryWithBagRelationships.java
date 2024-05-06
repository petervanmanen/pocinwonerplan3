package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Besluit;
import org.springframework.data.domain.Page;

public interface BesluitRepositoryWithBagRelationships {
    Optional<Besluit> fetchBagRelationships(Optional<Besluit> besluit);

    List<Besluit> fetchBagRelationships(List<Besluit> besluits);

    Page<Besluit> fetchBagRelationships(Page<Besluit> besluits);
}
