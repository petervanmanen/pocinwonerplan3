package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Bruikleen;
import org.springframework.data.domain.Page;

public interface BruikleenRepositoryWithBagRelationships {
    Optional<Bruikleen> fetchBagRelationships(Optional<Bruikleen> bruikleen);

    List<Bruikleen> fetchBagRelationships(List<Bruikleen> bruikleens);

    Page<Bruikleen> fetchBagRelationships(Page<Bruikleen> bruikleens);
}
