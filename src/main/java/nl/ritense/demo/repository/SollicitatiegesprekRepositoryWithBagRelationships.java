package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Sollicitatiegesprek;
import org.springframework.data.domain.Page;

public interface SollicitatiegesprekRepositoryWithBagRelationships {
    Optional<Sollicitatiegesprek> fetchBagRelationships(Optional<Sollicitatiegesprek> sollicitatiegesprek);

    List<Sollicitatiegesprek> fetchBagRelationships(List<Sollicitatiegesprek> sollicitatiegespreks);

    Page<Sollicitatiegesprek> fetchBagRelationships(Page<Sollicitatiegesprek> sollicitatiegespreks);
}
