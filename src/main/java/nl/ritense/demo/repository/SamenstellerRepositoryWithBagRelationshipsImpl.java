package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Samensteller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SamenstellerRepositoryWithBagRelationshipsImpl implements SamenstellerRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SAMENSTELLERS_PARAMETER = "samenstellers";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Samensteller> fetchBagRelationships(Optional<Samensteller> samensteller) {
        return samensteller.map(this::fetchSteltsamenTentoonstellings);
    }

    @Override
    public Page<Samensteller> fetchBagRelationships(Page<Samensteller> samenstellers) {
        return new PageImpl<>(
            fetchBagRelationships(samenstellers.getContent()),
            samenstellers.getPageable(),
            samenstellers.getTotalElements()
        );
    }

    @Override
    public List<Samensteller> fetchBagRelationships(List<Samensteller> samenstellers) {
        return Optional.of(samenstellers).map(this::fetchSteltsamenTentoonstellings).orElse(Collections.emptyList());
    }

    Samensteller fetchSteltsamenTentoonstellings(Samensteller result) {
        return entityManager
            .createQuery(
                "select samensteller from Samensteller samensteller left join fetch samensteller.steltsamenTentoonstellings where samensteller.id = :id",
                Samensteller.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Samensteller> fetchSteltsamenTentoonstellings(List<Samensteller> samenstellers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, samenstellers.size()).forEach(index -> order.put(samenstellers.get(index).getId(), index));
        List<Samensteller> result = entityManager
            .createQuery(
                "select samensteller from Samensteller samensteller left join fetch samensteller.steltsamenTentoonstellings where samensteller in :samenstellers",
                Samensteller.class
            )
            .setParameter(SAMENSTELLERS_PARAMETER, samenstellers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
