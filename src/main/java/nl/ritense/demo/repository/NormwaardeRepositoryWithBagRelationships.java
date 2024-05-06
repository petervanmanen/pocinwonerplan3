package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Normwaarde;
import org.springframework.data.domain.Page;

public interface NormwaardeRepositoryWithBagRelationships {
    Optional<Normwaarde> fetchBagRelationships(Optional<Normwaarde> normwaarde);

    List<Normwaarde> fetchBagRelationships(List<Normwaarde> normwaardes);

    Page<Normwaarde> fetchBagRelationships(Page<Normwaarde> normwaardes);
}
