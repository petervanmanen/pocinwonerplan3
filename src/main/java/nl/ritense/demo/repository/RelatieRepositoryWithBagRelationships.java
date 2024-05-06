package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Relatie;
import org.springframework.data.domain.Page;

public interface RelatieRepositoryWithBagRelationships {
    Optional<Relatie> fetchBagRelationships(Optional<Relatie> relatie);

    List<Relatie> fetchBagRelationships(List<Relatie> relaties);

    Page<Relatie> fetchBagRelationships(Page<Relatie> relaties);
}
