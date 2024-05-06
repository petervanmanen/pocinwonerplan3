package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Hoofdstuk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class HoofdstukRepositoryWithBagRelationshipsImpl implements HoofdstukRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String HOOFDSTUKS_PARAMETER = "hoofdstuks";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Hoofdstuk> fetchBagRelationships(Optional<Hoofdstuk> hoofdstuk) {
        return hoofdstuk.map(this::fetchBinnenPeriodes);
    }

    @Override
    public Page<Hoofdstuk> fetchBagRelationships(Page<Hoofdstuk> hoofdstuks) {
        return new PageImpl<>(fetchBagRelationships(hoofdstuks.getContent()), hoofdstuks.getPageable(), hoofdstuks.getTotalElements());
    }

    @Override
    public List<Hoofdstuk> fetchBagRelationships(List<Hoofdstuk> hoofdstuks) {
        return Optional.of(hoofdstuks).map(this::fetchBinnenPeriodes).orElse(Collections.emptyList());
    }

    Hoofdstuk fetchBinnenPeriodes(Hoofdstuk result) {
        return entityManager
            .createQuery(
                "select hoofdstuk from Hoofdstuk hoofdstuk left join fetch hoofdstuk.binnenPeriodes where hoofdstuk.id = :id",
                Hoofdstuk.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Hoofdstuk> fetchBinnenPeriodes(List<Hoofdstuk> hoofdstuks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, hoofdstuks.size()).forEach(index -> order.put(hoofdstuks.get(index).getId(), index));
        List<Hoofdstuk> result = entityManager
            .createQuery(
                "select hoofdstuk from Hoofdstuk hoofdstuk left join fetch hoofdstuk.binnenPeriodes where hoofdstuk in :hoofdstuks",
                Hoofdstuk.class
            )
            .setParameter(HOOFDSTUKS_PARAMETER, hoofdstuks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
