package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Applicatie;
import org.springframework.data.domain.Page;

public interface ApplicatieRepositoryWithBagRelationships {
    Optional<Applicatie> fetchBagRelationships(Optional<Applicatie> applicatie);

    List<Applicatie> fetchBagRelationships(List<Applicatie> applicaties);

    Page<Applicatie> fetchBagRelationships(Page<Applicatie> applicaties);
}
