package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Storting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class StortingRepositoryWithBagRelationshipsImpl implements StortingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String STORTINGS_PARAMETER = "stortings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Storting> fetchBagRelationships(Optional<Storting> storting) {
        return storting.map(this::fetchFractieFracties);
    }

    @Override
    public Page<Storting> fetchBagRelationships(Page<Storting> stortings) {
        return new PageImpl<>(fetchBagRelationships(stortings.getContent()), stortings.getPageable(), stortings.getTotalElements());
    }

    @Override
    public List<Storting> fetchBagRelationships(List<Storting> stortings) {
        return Optional.of(stortings).map(this::fetchFractieFracties).orElse(Collections.emptyList());
    }

    Storting fetchFractieFracties(Storting result) {
        return entityManager
            .createQuery(
                "select storting from Storting storting left join fetch storting.fractieFracties where storting.id = :id",
                Storting.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Storting> fetchFractieFracties(List<Storting> stortings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, stortings.size()).forEach(index -> order.put(stortings.get(index).getId(), index));
        List<Storting> result = entityManager
            .createQuery(
                "select storting from Storting storting left join fetch storting.fractieFracties where storting in :stortings",
                Storting.class
            )
            .setParameter(STORTINGS_PARAMETER, stortings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
