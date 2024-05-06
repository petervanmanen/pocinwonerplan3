package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Put;
import org.springframework.data.domain.Page;

public interface PutRepositoryWithBagRelationships {
    Optional<Put> fetchBagRelationships(Optional<Put> put);

    List<Put> fetchBagRelationships(List<Put> puts);

    Page<Put> fetchBagRelationships(Page<Put> puts);
}
