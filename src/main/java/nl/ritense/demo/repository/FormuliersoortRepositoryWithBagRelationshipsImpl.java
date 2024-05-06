package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Formuliersoort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FormuliersoortRepositoryWithBagRelationshipsImpl implements FormuliersoortRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String FORMULIERSOORTS_PARAMETER = "formuliersoorts";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Formuliersoort> fetchBagRelationships(Optional<Formuliersoort> formuliersoort) {
        return formuliersoort.map(this::fetchIsaanleidingvoorZaaktypes);
    }

    @Override
    public Page<Formuliersoort> fetchBagRelationships(Page<Formuliersoort> formuliersoorts) {
        return new PageImpl<>(
            fetchBagRelationships(formuliersoorts.getContent()),
            formuliersoorts.getPageable(),
            formuliersoorts.getTotalElements()
        );
    }

    @Override
    public List<Formuliersoort> fetchBagRelationships(List<Formuliersoort> formuliersoorts) {
        return Optional.of(formuliersoorts).map(this::fetchIsaanleidingvoorZaaktypes).orElse(Collections.emptyList());
    }

    Formuliersoort fetchIsaanleidingvoorZaaktypes(Formuliersoort result) {
        return entityManager
            .createQuery(
                "select formuliersoort from Formuliersoort formuliersoort left join fetch formuliersoort.isaanleidingvoorZaaktypes where formuliersoort.id = :id",
                Formuliersoort.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Formuliersoort> fetchIsaanleidingvoorZaaktypes(List<Formuliersoort> formuliersoorts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, formuliersoorts.size()).forEach(index -> order.put(formuliersoorts.get(index).getId(), index));
        List<Formuliersoort> result = entityManager
            .createQuery(
                "select formuliersoort from Formuliersoort formuliersoort left join fetch formuliersoort.isaanleidingvoorZaaktypes where formuliersoort in :formuliersoorts",
                Formuliersoort.class
            )
            .setParameter(FORMULIERSOORTS_PARAMETER, formuliersoorts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
