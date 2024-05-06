package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Betrokkene;
import org.springframework.data.domain.Page;

public interface BetrokkeneRepositoryWithBagRelationships {
    Optional<Betrokkene> fetchBagRelationships(Optional<Betrokkene> betrokkene);

    List<Betrokkene> fetchBagRelationships(List<Betrokkene> betrokkenes);

    Page<Betrokkene> fetchBagRelationships(Page<Betrokkene> betrokkenes);
}
