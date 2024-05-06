package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Kostenplaats;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class KostenplaatsRepositoryWithBagRelationshipsImpl implements KostenplaatsRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String KOSTENPLAATS_PARAMETER = "kostenplaats";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Kostenplaats> fetchBagRelationships(Optional<Kostenplaats> kostenplaats) {
        return kostenplaats.map(this::fetchHeeftInkooporders).map(this::fetchHeeftTaakvelds);
    }

    @Override
    public Page<Kostenplaats> fetchBagRelationships(Page<Kostenplaats> kostenplaats) {
        return new PageImpl<>(
            fetchBagRelationships(kostenplaats.getContent()),
            kostenplaats.getPageable(),
            kostenplaats.getTotalElements()
        );
    }

    @Override
    public List<Kostenplaats> fetchBagRelationships(List<Kostenplaats> kostenplaats) {
        return Optional.of(kostenplaats).map(this::fetchHeeftInkooporders).map(this::fetchHeeftTaakvelds).orElse(Collections.emptyList());
    }

    Kostenplaats fetchHeeftInkooporders(Kostenplaats result) {
        return entityManager
            .createQuery(
                "select kostenplaats from Kostenplaats kostenplaats left join fetch kostenplaats.heeftInkooporders where kostenplaats.id = :id",
                Kostenplaats.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Kostenplaats> fetchHeeftInkooporders(List<Kostenplaats> kostenplaats) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, kostenplaats.size()).forEach(index -> order.put(kostenplaats.get(index).getId(), index));
        List<Kostenplaats> result = entityManager
            .createQuery(
                "select kostenplaats from Kostenplaats kostenplaats left join fetch kostenplaats.heeftInkooporders where kostenplaats in :kostenplaats",
                Kostenplaats.class
            )
            .setParameter(KOSTENPLAATS_PARAMETER, kostenplaats)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Kostenplaats fetchHeeftTaakvelds(Kostenplaats result) {
        return entityManager
            .createQuery(
                "select kostenplaats from Kostenplaats kostenplaats left join fetch kostenplaats.heeftTaakvelds where kostenplaats.id = :id",
                Kostenplaats.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Kostenplaats> fetchHeeftTaakvelds(List<Kostenplaats> kostenplaats) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, kostenplaats.size()).forEach(index -> order.put(kostenplaats.get(index).getId(), index));
        List<Kostenplaats> result = entityManager
            .createQuery(
                "select kostenplaats from Kostenplaats kostenplaats left join fetch kostenplaats.heeftTaakvelds where kostenplaats in :kostenplaats",
                Kostenplaats.class
            )
            .setParameter(KOSTENPLAATS_PARAMETER, kostenplaats)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
