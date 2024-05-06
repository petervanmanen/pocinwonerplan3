package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Normwaarde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class NormwaardeRepositoryWithBagRelationshipsImpl implements NormwaardeRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String NORMWAARDES_PARAMETER = "normwaardes";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Normwaarde> fetchBagRelationships(Optional<Normwaarde> normwaarde) {
        return normwaarde.map(this::fetchGeldtvoorLocaties);
    }

    @Override
    public Page<Normwaarde> fetchBagRelationships(Page<Normwaarde> normwaardes) {
        return new PageImpl<>(fetchBagRelationships(normwaardes.getContent()), normwaardes.getPageable(), normwaardes.getTotalElements());
    }

    @Override
    public List<Normwaarde> fetchBagRelationships(List<Normwaarde> normwaardes) {
        return Optional.of(normwaardes).map(this::fetchGeldtvoorLocaties).orElse(Collections.emptyList());
    }

    Normwaarde fetchGeldtvoorLocaties(Normwaarde result) {
        return entityManager
            .createQuery(
                "select normwaarde from Normwaarde normwaarde left join fetch normwaarde.geldtvoorLocaties where normwaarde.id = :id",
                Normwaarde.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Normwaarde> fetchGeldtvoorLocaties(List<Normwaarde> normwaardes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, normwaardes.size()).forEach(index -> order.put(normwaardes.get(index).getId(), index));
        List<Normwaarde> result = entityManager
            .createQuery(
                "select normwaarde from Normwaarde normwaarde left join fetch normwaarde.geldtvoorLocaties where normwaarde in :normwaardes",
                Normwaarde.class
            )
            .setParameter(NORMWAARDES_PARAMETER, normwaardes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
