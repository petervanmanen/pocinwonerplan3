package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Leverancier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class LeverancierRepositoryWithBagRelationshipsImpl implements LeverancierRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String LEVERANCIERS_PARAMETER = "leveranciers";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Leverancier> fetchBagRelationships(Optional<Leverancier> leverancier) {
        return leverancier.map(this::fetchGekwalificeerdCategories);
    }

    @Override
    public Page<Leverancier> fetchBagRelationships(Page<Leverancier> leveranciers) {
        return new PageImpl<>(
            fetchBagRelationships(leveranciers.getContent()),
            leveranciers.getPageable(),
            leveranciers.getTotalElements()
        );
    }

    @Override
    public List<Leverancier> fetchBagRelationships(List<Leverancier> leveranciers) {
        return Optional.of(leveranciers).map(this::fetchGekwalificeerdCategories).orElse(Collections.emptyList());
    }

    Leverancier fetchGekwalificeerdCategories(Leverancier result) {
        return entityManager
            .createQuery(
                "select leverancier from Leverancier leverancier left join fetch leverancier.gekwalificeerdCategories where leverancier.id = :id",
                Leverancier.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Leverancier> fetchGekwalificeerdCategories(List<Leverancier> leveranciers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, leveranciers.size()).forEach(index -> order.put(leveranciers.get(index).getId(), index));
        List<Leverancier> result = entityManager
            .createQuery(
                "select leverancier from Leverancier leverancier left join fetch leverancier.gekwalificeerdCategories where leverancier in :leveranciers",
                Leverancier.class
            )
            .setParameter(LEVERANCIERS_PARAMETER, leveranciers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
