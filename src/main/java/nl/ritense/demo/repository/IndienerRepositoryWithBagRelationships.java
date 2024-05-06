package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Indiener;
import org.springframework.data.domain.Page;

public interface IndienerRepositoryWithBagRelationships {
    Optional<Indiener> fetchBagRelationships(Optional<Indiener> indiener);

    List<Indiener> fetchBagRelationships(List<Indiener> indieners);

    Page<Indiener> fetchBagRelationships(Page<Indiener> indieners);
}
