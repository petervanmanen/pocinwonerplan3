package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Relatie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RelatieRepositoryWithBagRelationshipsImpl implements RelatieRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String RELATIES_PARAMETER = "relaties";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Relatie> fetchBagRelationships(Optional<Relatie> relatie) {
        return relatie.map(this::fetchMaaktonderdeelvanHuishoudens);
    }

    @Override
    public Page<Relatie> fetchBagRelationships(Page<Relatie> relaties) {
        return new PageImpl<>(fetchBagRelationships(relaties.getContent()), relaties.getPageable(), relaties.getTotalElements());
    }

    @Override
    public List<Relatie> fetchBagRelationships(List<Relatie> relaties) {
        return Optional.of(relaties).map(this::fetchMaaktonderdeelvanHuishoudens).orElse(Collections.emptyList());
    }

    Relatie fetchMaaktonderdeelvanHuishoudens(Relatie result) {
        return entityManager
            .createQuery(
                "select relatie from Relatie relatie left join fetch relatie.maaktonderdeelvanHuishoudens where relatie.id = :id",
                Relatie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Relatie> fetchMaaktonderdeelvanHuishoudens(List<Relatie> relaties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, relaties.size()).forEach(index -> order.put(relaties.get(index).getId(), index));
        List<Relatie> result = entityManager
            .createQuery(
                "select relatie from Relatie relatie left join fetch relatie.maaktonderdeelvanHuishoudens where relatie in :relaties",
                Relatie.class
            )
            .setParameter(RELATIES_PARAMETER, relaties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
