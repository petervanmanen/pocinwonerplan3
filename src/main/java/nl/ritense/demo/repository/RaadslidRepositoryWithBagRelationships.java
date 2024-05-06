package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Raadslid;
import org.springframework.data.domain.Page;

public interface RaadslidRepositoryWithBagRelationships {
    Optional<Raadslid> fetchBagRelationships(Optional<Raadslid> raadslid);

    List<Raadslid> fetchBagRelationships(List<Raadslid> raadslids);

    Page<Raadslid> fetchBagRelationships(Page<Raadslid> raadslids);
}
