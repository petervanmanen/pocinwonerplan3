package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Bruikleen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BruikleenRepositoryWithBagRelationshipsImpl implements BruikleenRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String BRUIKLEENS_PARAMETER = "bruikleens";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Bruikleen> fetchBagRelationships(Optional<Bruikleen> bruikleen) {
        return bruikleen.map(this::fetchIsbedoeldvoorTentoonstellings);
    }

    @Override
    public Page<Bruikleen> fetchBagRelationships(Page<Bruikleen> bruikleens) {
        return new PageImpl<>(fetchBagRelationships(bruikleens.getContent()), bruikleens.getPageable(), bruikleens.getTotalElements());
    }

    @Override
    public List<Bruikleen> fetchBagRelationships(List<Bruikleen> bruikleens) {
        return Optional.of(bruikleens).map(this::fetchIsbedoeldvoorTentoonstellings).orElse(Collections.emptyList());
    }

    Bruikleen fetchIsbedoeldvoorTentoonstellings(Bruikleen result) {
        return entityManager
            .createQuery(
                "select bruikleen from Bruikleen bruikleen left join fetch bruikleen.isbedoeldvoorTentoonstellings where bruikleen.id = :id",
                Bruikleen.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Bruikleen> fetchIsbedoeldvoorTentoonstellings(List<Bruikleen> bruikleens) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, bruikleens.size()).forEach(index -> order.put(bruikleens.get(index).getId(), index));
        List<Bruikleen> result = entityManager
            .createQuery(
                "select bruikleen from Bruikleen bruikleen left join fetch bruikleen.isbedoeldvoorTentoonstellings where bruikleen in :bruikleens",
                Bruikleen.class
            )
            .setParameter(BRUIKLEENS_PARAMETER, bruikleens)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
