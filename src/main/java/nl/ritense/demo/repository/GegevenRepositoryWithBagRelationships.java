package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Gegeven;
import org.springframework.data.domain.Page;

public interface GegevenRepositoryWithBagRelationships {
    Optional<Gegeven> fetchBagRelationships(Optional<Gegeven> gegeven);

    List<Gegeven> fetchBagRelationships(List<Gegeven> gegevens);

    Page<Gegeven> fetchBagRelationships(Page<Gegeven> gegevens);
}
