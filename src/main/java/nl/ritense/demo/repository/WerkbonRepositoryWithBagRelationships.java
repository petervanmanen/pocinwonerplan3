package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Werkbon;
import org.springframework.data.domain.Page;

public interface WerkbonRepositoryWithBagRelationships {
    Optional<Werkbon> fetchBagRelationships(Optional<Werkbon> werkbon);

    List<Werkbon> fetchBagRelationships(List<Werkbon> werkbons);

    Page<Werkbon> fetchBagRelationships(Page<Werkbon> werkbons);
}
