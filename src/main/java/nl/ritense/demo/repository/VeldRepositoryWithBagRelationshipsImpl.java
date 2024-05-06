package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Veld;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VeldRepositoryWithBagRelationshipsImpl implements VeldRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String VELDS_PARAMETER = "velds";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Veld> fetchBagRelationships(Optional<Veld> veld) {
        return veld.map(this::fetchHeeftBelijnings);
    }

    @Override
    public Page<Veld> fetchBagRelationships(Page<Veld> velds) {
        return new PageImpl<>(fetchBagRelationships(velds.getContent()), velds.getPageable(), velds.getTotalElements());
    }

    @Override
    public List<Veld> fetchBagRelationships(List<Veld> velds) {
        return Optional.of(velds).map(this::fetchHeeftBelijnings).orElse(Collections.emptyList());
    }

    Veld fetchHeeftBelijnings(Veld result) {
        return entityManager
            .createQuery("select veld from Veld veld left join fetch veld.heeftBelijnings where veld.id = :id", Veld.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Veld> fetchHeeftBelijnings(List<Veld> velds) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, velds.size()).forEach(index -> order.put(velds.get(index).getId(), index));
        List<Veld> result = entityManager
            .createQuery("select veld from Veld veld left join fetch veld.heeftBelijnings where veld in :velds", Veld.class)
            .setParameter(VELDS_PARAMETER, velds)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
