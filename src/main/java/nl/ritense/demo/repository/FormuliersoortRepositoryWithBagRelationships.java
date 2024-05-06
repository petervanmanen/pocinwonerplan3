package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Formuliersoort;
import org.springframework.data.domain.Page;

public interface FormuliersoortRepositoryWithBagRelationships {
    Optional<Formuliersoort> fetchBagRelationships(Optional<Formuliersoort> formuliersoort);

    List<Formuliersoort> fetchBagRelationships(List<Formuliersoort> formuliersoorts);

    Page<Formuliersoort> fetchBagRelationships(Page<Formuliersoort> formuliersoorts);
}
