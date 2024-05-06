package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Clientbegeleider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ClientbegeleiderRepositoryWithBagRelationshipsImpl implements ClientbegeleiderRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String CLIENTBEGELEIDERS_PARAMETER = "clientbegeleiders";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Clientbegeleider> fetchBagRelationships(Optional<Clientbegeleider> clientbegeleider) {
        return clientbegeleider.map(this::fetchOndersteuntclientClients);
    }

    @Override
    public Page<Clientbegeleider> fetchBagRelationships(Page<Clientbegeleider> clientbegeleiders) {
        return new PageImpl<>(
            fetchBagRelationships(clientbegeleiders.getContent()),
            clientbegeleiders.getPageable(),
            clientbegeleiders.getTotalElements()
        );
    }

    @Override
    public List<Clientbegeleider> fetchBagRelationships(List<Clientbegeleider> clientbegeleiders) {
        return Optional.of(clientbegeleiders).map(this::fetchOndersteuntclientClients).orElse(Collections.emptyList());
    }

    Clientbegeleider fetchOndersteuntclientClients(Clientbegeleider result) {
        return entityManager
            .createQuery(
                "select clientbegeleider from Clientbegeleider clientbegeleider left join fetch clientbegeleider.ondersteuntclientClients where clientbegeleider.id = :id",
                Clientbegeleider.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Clientbegeleider> fetchOndersteuntclientClients(List<Clientbegeleider> clientbegeleiders) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, clientbegeleiders.size()).forEach(index -> order.put(clientbegeleiders.get(index).getId(), index));
        List<Clientbegeleider> result = entityManager
            .createQuery(
                "select clientbegeleider from Clientbegeleider clientbegeleider left join fetch clientbegeleider.ondersteuntclientClients where clientbegeleider in :clientbegeleiders",
                Clientbegeleider.class
            )
            .setParameter(CLIENTBEGELEIDERS_PARAMETER, clientbegeleiders)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
