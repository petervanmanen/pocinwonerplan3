package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Collectie;
import org.springframework.data.domain.Page;

public interface CollectieRepositoryWithBagRelationships {
    Optional<Collectie> fetchBagRelationships(Optional<Collectie> collectie);

    List<Collectie> fetchBagRelationships(List<Collectie> collecties);

    Page<Collectie> fetchBagRelationships(Page<Collectie> collecties);
}
