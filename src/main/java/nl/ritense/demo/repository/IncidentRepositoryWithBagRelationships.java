package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Incident;
import org.springframework.data.domain.Page;

public interface IncidentRepositoryWithBagRelationships {
    Optional<Incident> fetchBagRelationships(Optional<Incident> incident);

    List<Incident> fetchBagRelationships(List<Incident> incidents);

    Page<Incident> fetchBagRelationships(Page<Incident> incidents);
}
