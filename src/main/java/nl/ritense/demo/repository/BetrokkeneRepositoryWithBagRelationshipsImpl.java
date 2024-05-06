package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Betrokkene;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BetrokkeneRepositoryWithBagRelationshipsImpl implements BetrokkeneRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String BETROKKENES_PARAMETER = "betrokkenes";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Betrokkene> fetchBagRelationships(Optional<Betrokkene> betrokkene) {
        return betrokkene.map(this::fetchOefentuitZaaks);
    }

    @Override
    public Page<Betrokkene> fetchBagRelationships(Page<Betrokkene> betrokkenes) {
        return new PageImpl<>(fetchBagRelationships(betrokkenes.getContent()), betrokkenes.getPageable(), betrokkenes.getTotalElements());
    }

    @Override
    public List<Betrokkene> fetchBagRelationships(List<Betrokkene> betrokkenes) {
        return Optional.of(betrokkenes).map(this::fetchOefentuitZaaks).orElse(Collections.emptyList());
    }

    Betrokkene fetchOefentuitZaaks(Betrokkene result) {
        return entityManager
            .createQuery(
                "select betrokkene from Betrokkene betrokkene left join fetch betrokkene.oefentuitZaaks where betrokkene.id = :id",
                Betrokkene.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Betrokkene> fetchOefentuitZaaks(List<Betrokkene> betrokkenes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, betrokkenes.size()).forEach(index -> order.put(betrokkenes.get(index).getId(), index));
        List<Betrokkene> result = entityManager
            .createQuery(
                "select betrokkene from Betrokkene betrokkene left join fetch betrokkene.oefentuitZaaks where betrokkene in :betrokkenes",
                Betrokkene.class
            )
            .setParameter(BETROKKENES_PARAMETER, betrokkenes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
