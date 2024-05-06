package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Hoofdrekening;
import org.springframework.data.domain.Page;

public interface HoofdrekeningRepositoryWithBagRelationships {
    Optional<Hoofdrekening> fetchBagRelationships(Optional<Hoofdrekening> hoofdrekening);

    List<Hoofdrekening> fetchBagRelationships(List<Hoofdrekening> hoofdrekenings);

    Page<Hoofdrekening> fetchBagRelationships(Page<Hoofdrekening> hoofdrekenings);
}
