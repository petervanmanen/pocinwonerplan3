package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Leverancier;
import org.springframework.data.domain.Page;

public interface LeverancierRepositoryWithBagRelationships {
    Optional<Leverancier> fetchBagRelationships(Optional<Leverancier> leverancier);

    List<Leverancier> fetchBagRelationships(List<Leverancier> leveranciers);

    Page<Leverancier> fetchBagRelationships(Page<Leverancier> leveranciers);
}
