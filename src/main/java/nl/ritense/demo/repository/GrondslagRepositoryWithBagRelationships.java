package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Grondslag;
import org.springframework.data.domain.Page;

public interface GrondslagRepositoryWithBagRelationships {
    Optional<Grondslag> fetchBagRelationships(Optional<Grondslag> grondslag);

    List<Grondslag> fetchBagRelationships(List<Grondslag> grondslags);

    Page<Grondslag> fetchBagRelationships(Page<Grondslag> grondslags);
}
