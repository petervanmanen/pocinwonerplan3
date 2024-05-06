package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Put;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PutRepositoryWithBagRelationshipsImpl implements PutRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PUTS_PARAMETER = "puts";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Put> fetchBagRelationships(Optional<Put> put) {
        return put.map(this::fetchHeeftlocatieLocaties);
    }

    @Override
    public Page<Put> fetchBagRelationships(Page<Put> puts) {
        return new PageImpl<>(fetchBagRelationships(puts.getContent()), puts.getPageable(), puts.getTotalElements());
    }

    @Override
    public List<Put> fetchBagRelationships(List<Put> puts) {
        return Optional.of(puts).map(this::fetchHeeftlocatieLocaties).orElse(Collections.emptyList());
    }

    Put fetchHeeftlocatieLocaties(Put result) {
        return entityManager
            .createQuery("select put from Put put left join fetch put.heeftlocatieLocaties where put.id = :id", Put.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Put> fetchHeeftlocatieLocaties(List<Put> puts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, puts.size()).forEach(index -> order.put(puts.get(index).getId(), index));
        List<Put> result = entityManager
            .createQuery("select put from Put put left join fetch put.heeftlocatieLocaties where put in :puts", Put.class)
            .setParameter(PUTS_PARAMETER, puts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
