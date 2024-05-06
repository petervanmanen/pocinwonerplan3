package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Hoofdrekening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class HoofdrekeningRepositoryWithBagRelationshipsImpl implements HoofdrekeningRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String HOOFDREKENINGS_PARAMETER = "hoofdrekenings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Hoofdrekening> fetchBagRelationships(Optional<Hoofdrekening> hoofdrekening) {
        return hoofdrekening.map(this::fetchHeeftActivas).map(this::fetchHeeftKostenplaats);
    }

    @Override
    public Page<Hoofdrekening> fetchBagRelationships(Page<Hoofdrekening> hoofdrekenings) {
        return new PageImpl<>(
            fetchBagRelationships(hoofdrekenings.getContent()),
            hoofdrekenings.getPageable(),
            hoofdrekenings.getTotalElements()
        );
    }

    @Override
    public List<Hoofdrekening> fetchBagRelationships(List<Hoofdrekening> hoofdrekenings) {
        return Optional.of(hoofdrekenings).map(this::fetchHeeftActivas).map(this::fetchHeeftKostenplaats).orElse(Collections.emptyList());
    }

    Hoofdrekening fetchHeeftActivas(Hoofdrekening result) {
        return entityManager
            .createQuery(
                "select hoofdrekening from Hoofdrekening hoofdrekening left join fetch hoofdrekening.heeftActivas where hoofdrekening.id = :id",
                Hoofdrekening.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Hoofdrekening> fetchHeeftActivas(List<Hoofdrekening> hoofdrekenings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, hoofdrekenings.size()).forEach(index -> order.put(hoofdrekenings.get(index).getId(), index));
        List<Hoofdrekening> result = entityManager
            .createQuery(
                "select hoofdrekening from Hoofdrekening hoofdrekening left join fetch hoofdrekening.heeftActivas where hoofdrekening in :hoofdrekenings",
                Hoofdrekening.class
            )
            .setParameter(HOOFDREKENINGS_PARAMETER, hoofdrekenings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Hoofdrekening fetchHeeftKostenplaats(Hoofdrekening result) {
        return entityManager
            .createQuery(
                "select hoofdrekening from Hoofdrekening hoofdrekening left join fetch hoofdrekening.heeftKostenplaats where hoofdrekening.id = :id",
                Hoofdrekening.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Hoofdrekening> fetchHeeftKostenplaats(List<Hoofdrekening> hoofdrekenings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, hoofdrekenings.size()).forEach(index -> order.put(hoofdrekenings.get(index).getId(), index));
        List<Hoofdrekening> result = entityManager
            .createQuery(
                "select hoofdrekening from Hoofdrekening hoofdrekening left join fetch hoofdrekening.heeftKostenplaats where hoofdrekening in :hoofdrekenings",
                Hoofdrekening.class
            )
            .setParameter(HOOFDREKENINGS_PARAMETER, hoofdrekenings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
