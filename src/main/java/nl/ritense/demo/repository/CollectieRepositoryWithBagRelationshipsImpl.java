package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Collectie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CollectieRepositoryWithBagRelationshipsImpl implements CollectieRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String COLLECTIES_PARAMETER = "collecties";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Collectie> fetchBagRelationships(Optional<Collectie> collectie) {
        return collectie.map(this::fetchBevatMuseumobjects);
    }

    @Override
    public Page<Collectie> fetchBagRelationships(Page<Collectie> collecties) {
        return new PageImpl<>(fetchBagRelationships(collecties.getContent()), collecties.getPageable(), collecties.getTotalElements());
    }

    @Override
    public List<Collectie> fetchBagRelationships(List<Collectie> collecties) {
        return Optional.of(collecties).map(this::fetchBevatMuseumobjects).orElse(Collections.emptyList());
    }

    Collectie fetchBevatMuseumobjects(Collectie result) {
        return entityManager
            .createQuery(
                "select collectie from Collectie collectie left join fetch collectie.bevatMuseumobjects where collectie.id = :id",
                Collectie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Collectie> fetchBevatMuseumobjects(List<Collectie> collecties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, collecties.size()).forEach(index -> order.put(collecties.get(index).getId(), index));
        List<Collectie> result = entityManager
            .createQuery(
                "select collectie from Collectie collectie left join fetch collectie.bevatMuseumobjects where collectie in :collecties",
                Collectie.class
            )
            .setParameter(COLLECTIES_PARAMETER, collecties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
