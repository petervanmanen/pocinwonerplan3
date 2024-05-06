package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Aanvraag;
import org.springframework.data.domain.Page;

public interface AanvraagRepositoryWithBagRelationships {
    Optional<Aanvraag> fetchBagRelationships(Optional<Aanvraag> aanvraag);

    List<Aanvraag> fetchBagRelationships(List<Aanvraag> aanvraags);

    Page<Aanvraag> fetchBagRelationships(Page<Aanvraag> aanvraags);
}
