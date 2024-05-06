package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Gegeven;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class GegevenRepositoryWithBagRelationshipsImpl implements GegevenRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String GEGEVENS_PARAMETER = "gegevens";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Gegeven> fetchBagRelationships(Optional<Gegeven> gegeven) {
        return gegeven.map(this::fetchGeclassificeerdalsClassificaties);
    }

    @Override
    public Page<Gegeven> fetchBagRelationships(Page<Gegeven> gegevens) {
        return new PageImpl<>(fetchBagRelationships(gegevens.getContent()), gegevens.getPageable(), gegevens.getTotalElements());
    }

    @Override
    public List<Gegeven> fetchBagRelationships(List<Gegeven> gegevens) {
        return Optional.of(gegevens).map(this::fetchGeclassificeerdalsClassificaties).orElse(Collections.emptyList());
    }

    Gegeven fetchGeclassificeerdalsClassificaties(Gegeven result) {
        return entityManager
            .createQuery(
                "select gegeven from Gegeven gegeven left join fetch gegeven.geclassificeerdalsClassificaties where gegeven.id = :id",
                Gegeven.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Gegeven> fetchGeclassificeerdalsClassificaties(List<Gegeven> gegevens) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, gegevens.size()).forEach(index -> order.put(gegevens.get(index).getId(), index));
        List<Gegeven> result = entityManager
            .createQuery(
                "select gegeven from Gegeven gegeven left join fetch gegeven.geclassificeerdalsClassificaties where gegeven in :gegevens",
                Gegeven.class
            )
            .setParameter(GEGEVENS_PARAMETER, gegevens)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
