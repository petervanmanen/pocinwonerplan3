package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Pas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PasRepositoryWithBagRelationshipsImpl implements PasRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PAS_PARAMETER = "pas";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Pas> fetchBagRelationships(Optional<Pas> pas) {
        return pas.map(this::fetchGeldigvoorMilieustraats);
    }

    @Override
    public Page<Pas> fetchBagRelationships(Page<Pas> pas) {
        return new PageImpl<>(fetchBagRelationships(pas.getContent()), pas.getPageable(), pas.getTotalElements());
    }

    @Override
    public List<Pas> fetchBagRelationships(List<Pas> pas) {
        return Optional.of(pas).map(this::fetchGeldigvoorMilieustraats).orElse(Collections.emptyList());
    }

    Pas fetchGeldigvoorMilieustraats(Pas result) {
        return entityManager
            .createQuery("select pas from Pas pas left join fetch pas.geldigvoorMilieustraats where pas.id = :id", Pas.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Pas> fetchGeldigvoorMilieustraats(List<Pas> pas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, pas.size()).forEach(index -> order.put(pas.get(index).getId(), index));
        List<Pas> result = entityManager
            .createQuery("select pas from Pas pas left join fetch pas.geldigvoorMilieustraats where pas in :pas", Pas.class)
            .setParameter(PAS_PARAMETER, pas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
