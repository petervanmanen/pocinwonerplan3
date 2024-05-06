package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Pas;
import org.springframework.data.domain.Page;

public interface PasRepositoryWithBagRelationships {
    Optional<Pas> fetchBagRelationships(Optional<Pas> pas);

    List<Pas> fetchBagRelationships(List<Pas> pas);

    Page<Pas> fetchBagRelationships(Page<Pas> pas);
}
