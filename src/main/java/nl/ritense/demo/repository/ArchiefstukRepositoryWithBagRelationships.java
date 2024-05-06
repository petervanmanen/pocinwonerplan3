package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Archiefstuk;
import org.springframework.data.domain.Page;

public interface ArchiefstukRepositoryWithBagRelationships {
    Optional<Archiefstuk> fetchBagRelationships(Optional<Archiefstuk> archiefstuk);

    List<Archiefstuk> fetchBagRelationships(List<Archiefstuk> archiefstuks);

    Page<Archiefstuk> fetchBagRelationships(Page<Archiefstuk> archiefstuks);
}
