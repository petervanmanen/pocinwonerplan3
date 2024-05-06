package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Verblijfsobject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VerblijfsobjectRepositoryWithBagRelationshipsImpl implements VerblijfsobjectRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String VERBLIJFSOBJECTS_PARAMETER = "verblijfsobjects";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Verblijfsobject> fetchBagRelationships(Optional<Verblijfsobject> verblijfsobject) {
        return verblijfsobject.map(this::fetchMaaktdeeluitvanPands);
    }

    @Override
    public Page<Verblijfsobject> fetchBagRelationships(Page<Verblijfsobject> verblijfsobjects) {
        return new PageImpl<>(
            fetchBagRelationships(verblijfsobjects.getContent()),
            verblijfsobjects.getPageable(),
            verblijfsobjects.getTotalElements()
        );
    }

    @Override
    public List<Verblijfsobject> fetchBagRelationships(List<Verblijfsobject> verblijfsobjects) {
        return Optional.of(verblijfsobjects).map(this::fetchMaaktdeeluitvanPands).orElse(Collections.emptyList());
    }

    Verblijfsobject fetchMaaktdeeluitvanPands(Verblijfsobject result) {
        return entityManager
            .createQuery(
                "select verblijfsobject from Verblijfsobject verblijfsobject left join fetch verblijfsobject.maaktdeeluitvanPands where verblijfsobject.id = :id",
                Verblijfsobject.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Verblijfsobject> fetchMaaktdeeluitvanPands(List<Verblijfsobject> verblijfsobjects) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, verblijfsobjects.size()).forEach(index -> order.put(verblijfsobjects.get(index).getId(), index));
        List<Verblijfsobject> result = entityManager
            .createQuery(
                "select verblijfsobject from Verblijfsobject verblijfsobject left join fetch verblijfsobject.maaktdeeluitvanPands where verblijfsobject in :verblijfsobjects",
                Verblijfsobject.class
            )
            .setParameter(VERBLIJFSOBJECTS_PARAMETER, verblijfsobjects)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
