package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Werknemer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WerknemerRepositoryWithBagRelationshipsImpl implements WerknemerRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String WERKNEMERS_PARAMETER = "werknemers";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Werknemer> fetchBagRelationships(Optional<Werknemer> werknemer) {
        return werknemer.map(this::fetchHeeftRols);
    }

    @Override
    public Page<Werknemer> fetchBagRelationships(Page<Werknemer> werknemers) {
        return new PageImpl<>(fetchBagRelationships(werknemers.getContent()), werknemers.getPageable(), werknemers.getTotalElements());
    }

    @Override
    public List<Werknemer> fetchBagRelationships(List<Werknemer> werknemers) {
        return Optional.of(werknemers).map(this::fetchHeeftRols).orElse(Collections.emptyList());
    }

    Werknemer fetchHeeftRols(Werknemer result) {
        return entityManager
            .createQuery(
                "select werknemer from Werknemer werknemer left join fetch werknemer.heeftRols where werknemer.id = :id",
                Werknemer.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Werknemer> fetchHeeftRols(List<Werknemer> werknemers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, werknemers.size()).forEach(index -> order.put(werknemers.get(index).getId(), index));
        List<Werknemer> result = entityManager
            .createQuery(
                "select werknemer from Werknemer werknemer left join fetch werknemer.heeftRols where werknemer in :werknemers",
                Werknemer.class
            )
            .setParameter(WERKNEMERS_PARAMETER, werknemers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
