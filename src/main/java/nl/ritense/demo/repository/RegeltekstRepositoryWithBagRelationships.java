package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Regeltekst;
import org.springframework.data.domain.Page;

public interface RegeltekstRepositoryWithBagRelationships {
    Optional<Regeltekst> fetchBagRelationships(Optional<Regeltekst> regeltekst);

    List<Regeltekst> fetchBagRelationships(List<Regeltekst> regelteksts);

    Page<Regeltekst> fetchBagRelationships(Page<Regeltekst> regelteksts);
}
