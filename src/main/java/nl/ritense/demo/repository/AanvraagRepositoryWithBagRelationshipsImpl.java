package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Aanvraag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AanvraagRepositoryWithBagRelationshipsImpl implements AanvraagRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String AANVRAAGS_PARAMETER = "aanvraags";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Aanvraag> fetchBagRelationships(Optional<Aanvraag> aanvraag) {
        return aanvraag.map(this::fetchVoorArchiefstuks);
    }

    @Override
    public Page<Aanvraag> fetchBagRelationships(Page<Aanvraag> aanvraags) {
        return new PageImpl<>(fetchBagRelationships(aanvraags.getContent()), aanvraags.getPageable(), aanvraags.getTotalElements());
    }

    @Override
    public List<Aanvraag> fetchBagRelationships(List<Aanvraag> aanvraags) {
        return Optional.of(aanvraags).map(this::fetchVoorArchiefstuks).orElse(Collections.emptyList());
    }

    Aanvraag fetchVoorArchiefstuks(Aanvraag result) {
        return entityManager
            .createQuery(
                "select aanvraag from Aanvraag aanvraag left join fetch aanvraag.voorArchiefstuks where aanvraag.id = :id",
                Aanvraag.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Aanvraag> fetchVoorArchiefstuks(List<Aanvraag> aanvraags) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, aanvraags.size()).forEach(index -> order.put(aanvraags.get(index).getId(), index));
        List<Aanvraag> result = entityManager
            .createQuery(
                "select aanvraag from Aanvraag aanvraag left join fetch aanvraag.voorArchiefstuks where aanvraag in :aanvraags",
                Aanvraag.class
            )
            .setParameter(AANVRAAGS_PARAMETER, aanvraags)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
