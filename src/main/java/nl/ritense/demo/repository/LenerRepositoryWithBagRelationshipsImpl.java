package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Lener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class LenerRepositoryWithBagRelationshipsImpl implements LenerRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String LENERS_PARAMETER = "leners";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Lener> fetchBagRelationships(Optional<Lener> lener) {
        return lener.map(this::fetchIsBruikleens);
    }

    @Override
    public Page<Lener> fetchBagRelationships(Page<Lener> leners) {
        return new PageImpl<>(fetchBagRelationships(leners.getContent()), leners.getPageable(), leners.getTotalElements());
    }

    @Override
    public List<Lener> fetchBagRelationships(List<Lener> leners) {
        return Optional.of(leners).map(this::fetchIsBruikleens).orElse(Collections.emptyList());
    }

    Lener fetchIsBruikleens(Lener result) {
        return entityManager
            .createQuery("select lener from Lener lener left join fetch lener.isBruikleens where lener.id = :id", Lener.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Lener> fetchIsBruikleens(List<Lener> leners) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, leners.size()).forEach(index -> order.put(leners.get(index).getId(), index));
        List<Lener> result = entityManager
            .createQuery("select lener from Lener lener left join fetch lener.isBruikleens where lener in :leners", Lener.class)
            .setParameter(LENERS_PARAMETER, leners)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
