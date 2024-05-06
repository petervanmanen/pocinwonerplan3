package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Verzoek;
import org.springframework.data.domain.Page;

public interface VerzoekRepositoryWithBagRelationships {
    Optional<Verzoek> fetchBagRelationships(Optional<Verzoek> verzoek);

    List<Verzoek> fetchBagRelationships(List<Verzoek> verzoeks);

    Page<Verzoek> fetchBagRelationships(Page<Verzoek> verzoeks);
}
