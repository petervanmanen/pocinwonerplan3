package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Samensteller;
import org.springframework.data.domain.Page;

public interface SamenstellerRepositoryWithBagRelationships {
    Optional<Samensteller> fetchBagRelationships(Optional<Samensteller> samensteller);

    List<Samensteller> fetchBagRelationships(List<Samensteller> samenstellers);

    Page<Samensteller> fetchBagRelationships(Page<Samensteller> samenstellers);
}
