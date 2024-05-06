package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Opleiding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class OpleidingRepositoryWithBagRelationshipsImpl implements OpleidingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String OPLEIDINGS_PARAMETER = "opleidings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Opleiding> fetchBagRelationships(Optional<Opleiding> opleiding) {
        return opleiding.map(this::fetchWordtgegevendoorOnderwijsinstituuts);
    }

    @Override
    public Page<Opleiding> fetchBagRelationships(Page<Opleiding> opleidings) {
        return new PageImpl<>(fetchBagRelationships(opleidings.getContent()), opleidings.getPageable(), opleidings.getTotalElements());
    }

    @Override
    public List<Opleiding> fetchBagRelationships(List<Opleiding> opleidings) {
        return Optional.of(opleidings).map(this::fetchWordtgegevendoorOnderwijsinstituuts).orElse(Collections.emptyList());
    }

    Opleiding fetchWordtgegevendoorOnderwijsinstituuts(Opleiding result) {
        return entityManager
            .createQuery(
                "select opleiding from Opleiding opleiding left join fetch opleiding.wordtgegevendoorOnderwijsinstituuts where opleiding.id = :id",
                Opleiding.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Opleiding> fetchWordtgegevendoorOnderwijsinstituuts(List<Opleiding> opleidings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, opleidings.size()).forEach(index -> order.put(opleidings.get(index).getId(), index));
        List<Opleiding> result = entityManager
            .createQuery(
                "select opleiding from Opleiding opleiding left join fetch opleiding.wordtgegevendoorOnderwijsinstituuts where opleiding in :opleidings",
                Opleiding.class
            )
            .setParameter(OPLEIDINGS_PARAMETER, opleidings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
