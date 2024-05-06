package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Hoofdstuk;
import org.springframework.data.domain.Page;

public interface HoofdstukRepositoryWithBagRelationships {
    Optional<Hoofdstuk> fetchBagRelationships(Optional<Hoofdstuk> hoofdstuk);

    List<Hoofdstuk> fetchBagRelationships(List<Hoofdstuk> hoofdstuks);

    Page<Hoofdstuk> fetchBagRelationships(Page<Hoofdstuk> hoofdstuks);
}
