package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Bedrijfsproces;
import org.springframework.data.domain.Page;

public interface BedrijfsprocesRepositoryWithBagRelationships {
    Optional<Bedrijfsproces> fetchBagRelationships(Optional<Bedrijfsproces> bedrijfsproces);

    List<Bedrijfsproces> fetchBagRelationships(List<Bedrijfsproces> bedrijfsproces);

    Page<Bedrijfsproces> fetchBagRelationships(Page<Bedrijfsproces> bedrijfsproces);
}
