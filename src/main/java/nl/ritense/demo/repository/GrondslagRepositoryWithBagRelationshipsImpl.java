package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Grondslag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class GrondslagRepositoryWithBagRelationshipsImpl implements GrondslagRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String GRONDSLAGS_PARAMETER = "grondslags";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Grondslag> fetchBagRelationships(Optional<Grondslag> grondslag) {
        return grondslag.map(this::fetchHeeftZaaks);
    }

    @Override
    public Page<Grondslag> fetchBagRelationships(Page<Grondslag> grondslags) {
        return new PageImpl<>(fetchBagRelationships(grondslags.getContent()), grondslags.getPageable(), grondslags.getTotalElements());
    }

    @Override
    public List<Grondslag> fetchBagRelationships(List<Grondslag> grondslags) {
        return Optional.of(grondslags).map(this::fetchHeeftZaaks).orElse(Collections.emptyList());
    }

    Grondslag fetchHeeftZaaks(Grondslag result) {
        return entityManager
            .createQuery(
                "select grondslag from Grondslag grondslag left join fetch grondslag.heeftZaaks where grondslag.id = :id",
                Grondslag.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Grondslag> fetchHeeftZaaks(List<Grondslag> grondslags) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, grondslags.size()).forEach(index -> order.put(grondslags.get(index).getId(), index));
        List<Grondslag> result = entityManager
            .createQuery(
                "select grondslag from Grondslag grondslag left join fetch grondslag.heeftZaaks where grondslag in :grondslags",
                Grondslag.class
            )
            .setParameter(GRONDSLAGS_PARAMETER, grondslags)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
