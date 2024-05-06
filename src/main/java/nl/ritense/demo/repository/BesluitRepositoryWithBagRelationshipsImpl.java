package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Besluit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BesluitRepositoryWithBagRelationshipsImpl implements BesluitRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String BESLUITS_PARAMETER = "besluits";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Besluit> fetchBagRelationships(Optional<Besluit> besluit) {
        return besluit.map(this::fetchKanvastgelegdzijnalsDocuments);
    }

    @Override
    public Page<Besluit> fetchBagRelationships(Page<Besluit> besluits) {
        return new PageImpl<>(fetchBagRelationships(besluits.getContent()), besluits.getPageable(), besluits.getTotalElements());
    }

    @Override
    public List<Besluit> fetchBagRelationships(List<Besluit> besluits) {
        return Optional.of(besluits).map(this::fetchKanvastgelegdzijnalsDocuments).orElse(Collections.emptyList());
    }

    Besluit fetchKanvastgelegdzijnalsDocuments(Besluit result) {
        return entityManager
            .createQuery(
                "select besluit from Besluit besluit left join fetch besluit.kanvastgelegdzijnalsDocuments where besluit.id = :id",
                Besluit.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Besluit> fetchKanvastgelegdzijnalsDocuments(List<Besluit> besluits) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, besluits.size()).forEach(index -> order.put(besluits.get(index).getId(), index));
        List<Besluit> result = entityManager
            .createQuery(
                "select besluit from Besluit besluit left join fetch besluit.kanvastgelegdzijnalsDocuments where besluit in :besluits",
                Besluit.class
            )
            .setParameter(BESLUITS_PARAMETER, besluits)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
