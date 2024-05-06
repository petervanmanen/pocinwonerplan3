package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Werknemer;
import org.springframework.data.domain.Page;

public interface WerknemerRepositoryWithBagRelationships {
    Optional<Werknemer> fetchBagRelationships(Optional<Werknemer> werknemer);

    List<Werknemer> fetchBagRelationships(List<Werknemer> werknemers);

    Page<Werknemer> fetchBagRelationships(Page<Werknemer> werknemers);
}
