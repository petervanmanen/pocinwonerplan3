package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Inkooporder;
import org.springframework.data.domain.Page;

public interface InkooporderRepositoryWithBagRelationships {
    Optional<Inkooporder> fetchBagRelationships(Optional<Inkooporder> inkooporder);

    List<Inkooporder> fetchBagRelationships(List<Inkooporder> inkooporders);

    Page<Inkooporder> fetchBagRelationships(Page<Inkooporder> inkooporders);
}
