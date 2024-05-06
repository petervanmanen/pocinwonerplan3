package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Sportvereniging;
import org.springframework.data.domain.Page;

public interface SportverenigingRepositoryWithBagRelationships {
    Optional<Sportvereniging> fetchBagRelationships(Optional<Sportvereniging> sportvereniging);

    List<Sportvereniging> fetchBagRelationships(List<Sportvereniging> sportverenigings);

    Page<Sportvereniging> fetchBagRelationships(Page<Sportvereniging> sportverenigings);
}
