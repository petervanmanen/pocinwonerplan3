package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Milieustraat;
import org.springframework.data.domain.Page;

public interface MilieustraatRepositoryWithBagRelationships {
    Optional<Milieustraat> fetchBagRelationships(Optional<Milieustraat> milieustraat);

    List<Milieustraat> fetchBagRelationships(List<Milieustraat> milieustraats);

    Page<Milieustraat> fetchBagRelationships(Page<Milieustraat> milieustraats);
}
