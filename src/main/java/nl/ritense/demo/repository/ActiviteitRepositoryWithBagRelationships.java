package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Activiteit;
import org.springframework.data.domain.Page;

public interface ActiviteitRepositoryWithBagRelationships {
    Optional<Activiteit> fetchBagRelationships(Optional<Activiteit> activiteit);

    List<Activiteit> fetchBagRelationships(List<Activiteit> activiteits);

    Page<Activiteit> fetchBagRelationships(Page<Activiteit> activiteits);
}
