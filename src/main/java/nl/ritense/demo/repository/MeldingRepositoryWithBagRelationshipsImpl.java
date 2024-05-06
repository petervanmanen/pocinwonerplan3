package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Melding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MeldingRepositoryWithBagRelationshipsImpl implements MeldingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MELDINGS_PARAMETER = "meldings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Melding> fetchBagRelationships(Optional<Melding> melding) {
        return melding.map(this::fetchBetreftBeheerobjects);
    }

    @Override
    public Page<Melding> fetchBagRelationships(Page<Melding> meldings) {
        return new PageImpl<>(fetchBagRelationships(meldings.getContent()), meldings.getPageable(), meldings.getTotalElements());
    }

    @Override
    public List<Melding> fetchBagRelationships(List<Melding> meldings) {
        return Optional.of(meldings).map(this::fetchBetreftBeheerobjects).orElse(Collections.emptyList());
    }

    Melding fetchBetreftBeheerobjects(Melding result) {
        return entityManager
            .createQuery(
                "select melding from Melding melding left join fetch melding.betreftBeheerobjects where melding.id = :id",
                Melding.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Melding> fetchBetreftBeheerobjects(List<Melding> meldings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, meldings.size()).forEach(index -> order.put(meldings.get(index).getId(), index));
        List<Melding> result = entityManager
            .createQuery(
                "select melding from Melding melding left join fetch melding.betreftBeheerobjects where melding in :meldings",
                Melding.class
            )
            .setParameter(MELDINGS_PARAMETER, meldings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
