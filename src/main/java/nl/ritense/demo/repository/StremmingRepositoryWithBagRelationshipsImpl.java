package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Stremming;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class StremmingRepositoryWithBagRelationshipsImpl implements StremmingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String STREMMINGS_PARAMETER = "stremmings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Stremming> fetchBagRelationships(Optional<Stremming> stremming) {
        return stremming.map(this::fetchBetreftWegdeels);
    }

    @Override
    public Page<Stremming> fetchBagRelationships(Page<Stremming> stremmings) {
        return new PageImpl<>(fetchBagRelationships(stremmings.getContent()), stremmings.getPageable(), stremmings.getTotalElements());
    }

    @Override
    public List<Stremming> fetchBagRelationships(List<Stremming> stremmings) {
        return Optional.of(stremmings).map(this::fetchBetreftWegdeels).orElse(Collections.emptyList());
    }

    Stremming fetchBetreftWegdeels(Stremming result) {
        return entityManager
            .createQuery(
                "select stremming from Stremming stremming left join fetch stremming.betreftWegdeels where stremming.id = :id",
                Stremming.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Stremming> fetchBetreftWegdeels(List<Stremming> stremmings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, stremmings.size()).forEach(index -> order.put(stremmings.get(index).getId(), index));
        List<Stremming> result = entityManager
            .createQuery(
                "select stremming from Stremming stremming left join fetch stremming.betreftWegdeels where stremming in :stremmings",
                Stremming.class
            )
            .setParameter(STREMMINGS_PARAMETER, stremmings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
