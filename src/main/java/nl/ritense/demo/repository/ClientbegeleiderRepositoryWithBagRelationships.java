package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Clientbegeleider;
import org.springframework.data.domain.Page;

public interface ClientbegeleiderRepositoryWithBagRelationships {
    Optional<Clientbegeleider> fetchBagRelationships(Optional<Clientbegeleider> clientbegeleider);

    List<Clientbegeleider> fetchBagRelationships(List<Clientbegeleider> clientbegeleiders);

    Page<Clientbegeleider> fetchBagRelationships(Page<Clientbegeleider> clientbegeleiders);
}
