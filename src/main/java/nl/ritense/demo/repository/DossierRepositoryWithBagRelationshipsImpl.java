package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Dossier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DossierRepositoryWithBagRelationshipsImpl implements DossierRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String DOSSIERS_PARAMETER = "dossiers";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Dossier> fetchBagRelationships(Optional<Dossier> dossier) {
        return dossier.map(this::fetchHoortbijRaadsstuks);
    }

    @Override
    public Page<Dossier> fetchBagRelationships(Page<Dossier> dossiers) {
        return new PageImpl<>(fetchBagRelationships(dossiers.getContent()), dossiers.getPageable(), dossiers.getTotalElements());
    }

    @Override
    public List<Dossier> fetchBagRelationships(List<Dossier> dossiers) {
        return Optional.of(dossiers).map(this::fetchHoortbijRaadsstuks).orElse(Collections.emptyList());
    }

    Dossier fetchHoortbijRaadsstuks(Dossier result) {
        return entityManager
            .createQuery(
                "select dossier from Dossier dossier left join fetch dossier.hoortbijRaadsstuks where dossier.id = :id",
                Dossier.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Dossier> fetchHoortbijRaadsstuks(List<Dossier> dossiers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, dossiers.size()).forEach(index -> order.put(dossiers.get(index).getId(), index));
        List<Dossier> result = entityManager
            .createQuery(
                "select dossier from Dossier dossier left join fetch dossier.hoortbijRaadsstuks where dossier in :dossiers",
                Dossier.class
            )
            .setParameter(DOSSIERS_PARAMETER, dossiers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
