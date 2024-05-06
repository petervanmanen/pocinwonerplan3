package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Inkooporder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class InkooporderRepositoryWithBagRelationshipsImpl implements InkooporderRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String INKOOPORDERS_PARAMETER = "inkooporders";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Inkooporder> fetchBagRelationships(Optional<Inkooporder> inkooporder) {
        return inkooporder.map(this::fetchWordtgeschrevenopHoofdrekenings);
    }

    @Override
    public Page<Inkooporder> fetchBagRelationships(Page<Inkooporder> inkooporders) {
        return new PageImpl<>(
            fetchBagRelationships(inkooporders.getContent()),
            inkooporders.getPageable(),
            inkooporders.getTotalElements()
        );
    }

    @Override
    public List<Inkooporder> fetchBagRelationships(List<Inkooporder> inkooporders) {
        return Optional.of(inkooporders).map(this::fetchWordtgeschrevenopHoofdrekenings).orElse(Collections.emptyList());
    }

    Inkooporder fetchWordtgeschrevenopHoofdrekenings(Inkooporder result) {
        return entityManager
            .createQuery(
                "select inkooporder from Inkooporder inkooporder left join fetch inkooporder.wordtgeschrevenopHoofdrekenings where inkooporder.id = :id",
                Inkooporder.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Inkooporder> fetchWordtgeschrevenopHoofdrekenings(List<Inkooporder> inkooporders) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, inkooporders.size()).forEach(index -> order.put(inkooporders.get(index).getId(), index));
        List<Inkooporder> result = entityManager
            .createQuery(
                "select inkooporder from Inkooporder inkooporder left join fetch inkooporder.wordtgeschrevenopHoofdrekenings where inkooporder in :inkooporders",
                Inkooporder.class
            )
            .setParameter(INKOOPORDERS_PARAMETER, inkooporders)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
