package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Client;
import org.springframework.data.domain.Page;

public interface ClientRepositoryWithBagRelationships {
    Optional<Client> fetchBagRelationships(Optional<Client> client);

    List<Client> fetchBagRelationships(List<Client> clients);

    Page<Client> fetchBagRelationships(Page<Client> clients);
}
