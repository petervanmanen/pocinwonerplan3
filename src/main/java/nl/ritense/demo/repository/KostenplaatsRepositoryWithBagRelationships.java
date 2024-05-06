package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Kostenplaats;
import org.springframework.data.domain.Page;

public interface KostenplaatsRepositoryWithBagRelationships {
    Optional<Kostenplaats> fetchBagRelationships(Optional<Kostenplaats> kostenplaats);

    List<Kostenplaats> fetchBagRelationships(List<Kostenplaats> kostenplaats);

    Page<Kostenplaats> fetchBagRelationships(Page<Kostenplaats> kostenplaats);
}
