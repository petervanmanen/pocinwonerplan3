package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Zaak;
import org.springframework.data.domain.Page;

public interface ZaakRepositoryWithBagRelationships {
    Optional<Zaak> fetchBagRelationships(Optional<Zaak> zaak);

    List<Zaak> fetchBagRelationships(List<Zaak> zaaks);

    Page<Zaak> fetchBagRelationships(Page<Zaak> zaaks);
}
