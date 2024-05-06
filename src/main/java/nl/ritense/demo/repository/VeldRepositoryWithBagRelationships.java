package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Veld;
import org.springframework.data.domain.Page;

public interface VeldRepositoryWithBagRelationships {
    Optional<Veld> fetchBagRelationships(Optional<Veld> veld);

    List<Veld> fetchBagRelationships(List<Veld> velds);

    Page<Veld> fetchBagRelationships(Page<Veld> velds);
}
