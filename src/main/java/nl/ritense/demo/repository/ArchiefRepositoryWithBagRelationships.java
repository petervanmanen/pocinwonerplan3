package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Archief;
import org.springframework.data.domain.Page;

public interface ArchiefRepositoryWithBagRelationships {
    Optional<Archief> fetchBagRelationships(Optional<Archief> archief);

    List<Archief> fetchBagRelationships(List<Archief> archiefs);

    Page<Archief> fetchBagRelationships(Page<Archief> archiefs);
}
