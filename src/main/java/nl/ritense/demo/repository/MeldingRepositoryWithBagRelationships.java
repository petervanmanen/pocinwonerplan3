package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Melding;
import org.springframework.data.domain.Page;

public interface MeldingRepositoryWithBagRelationships {
    Optional<Melding> fetchBagRelationships(Optional<Melding> melding);

    List<Melding> fetchBagRelationships(List<Melding> meldings);

    Page<Melding> fetchBagRelationships(Page<Melding> meldings);
}
