package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Indiener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class IndienerRepositoryWithBagRelationshipsImpl implements IndienerRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String INDIENERS_PARAMETER = "indieners";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Indiener> fetchBagRelationships(Optional<Indiener> indiener) {
        return indiener.map(this::fetchHeeftRaadsstuks);
    }

    @Override
    public Page<Indiener> fetchBagRelationships(Page<Indiener> indieners) {
        return new PageImpl<>(fetchBagRelationships(indieners.getContent()), indieners.getPageable(), indieners.getTotalElements());
    }

    @Override
    public List<Indiener> fetchBagRelationships(List<Indiener> indieners) {
        return Optional.of(indieners).map(this::fetchHeeftRaadsstuks).orElse(Collections.emptyList());
    }

    Indiener fetchHeeftRaadsstuks(Indiener result) {
        return entityManager
            .createQuery(
                "select indiener from Indiener indiener left join fetch indiener.heeftRaadsstuks where indiener.id = :id",
                Indiener.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Indiener> fetchHeeftRaadsstuks(List<Indiener> indieners) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, indieners.size()).forEach(index -> order.put(indieners.get(index).getId(), index));
        List<Indiener> result = entityManager
            .createQuery(
                "select indiener from Indiener indiener left join fetch indiener.heeftRaadsstuks where indiener in :indieners",
                Indiener.class
            )
            .setParameter(INDIENERS_PARAMETER, indieners)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
