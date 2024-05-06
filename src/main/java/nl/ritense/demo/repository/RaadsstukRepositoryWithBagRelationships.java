package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Raadsstuk;
import org.springframework.data.domain.Page;

public interface RaadsstukRepositoryWithBagRelationships {
    Optional<Raadsstuk> fetchBagRelationships(Optional<Raadsstuk> raadsstuk);

    List<Raadsstuk> fetchBagRelationships(List<Raadsstuk> raadsstuks);

    Page<Raadsstuk> fetchBagRelationships(Page<Raadsstuk> raadsstuks);
}
