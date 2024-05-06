package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Lener;
import org.springframework.data.domain.Page;

public interface LenerRepositoryWithBagRelationships {
    Optional<Lener> fetchBagRelationships(Optional<Lener> lener);

    List<Lener> fetchBagRelationships(List<Lener> leners);

    Page<Lener> fetchBagRelationships(Page<Lener> leners);
}
