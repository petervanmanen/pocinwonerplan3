package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Vuilniswagen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VuilniswagenRepositoryWithBagRelationshipsImpl implements VuilniswagenRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String VUILNISWAGENS_PARAMETER = "vuilniswagens";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Vuilniswagen> fetchBagRelationships(Optional<Vuilniswagen> vuilniswagen) {
        return vuilniswagen.map(this::fetchGeschiktvoorContainertypes);
    }

    @Override
    public Page<Vuilniswagen> fetchBagRelationships(Page<Vuilniswagen> vuilniswagens) {
        return new PageImpl<>(
            fetchBagRelationships(vuilniswagens.getContent()),
            vuilniswagens.getPageable(),
            vuilniswagens.getTotalElements()
        );
    }

    @Override
    public List<Vuilniswagen> fetchBagRelationships(List<Vuilniswagen> vuilniswagens) {
        return Optional.of(vuilniswagens).map(this::fetchGeschiktvoorContainertypes).orElse(Collections.emptyList());
    }

    Vuilniswagen fetchGeschiktvoorContainertypes(Vuilniswagen result) {
        return entityManager
            .createQuery(
                "select vuilniswagen from Vuilniswagen vuilniswagen left join fetch vuilniswagen.geschiktvoorContainertypes where vuilniswagen.id = :id",
                Vuilniswagen.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Vuilniswagen> fetchGeschiktvoorContainertypes(List<Vuilniswagen> vuilniswagens) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, vuilniswagens.size()).forEach(index -> order.put(vuilniswagens.get(index).getId(), index));
        List<Vuilniswagen> result = entityManager
            .createQuery(
                "select vuilniswagen from Vuilniswagen vuilniswagen left join fetch vuilniswagen.geschiktvoorContainertypes where vuilniswagen in :vuilniswagens",
                Vuilniswagen.class
            )
            .setParameter(VUILNISWAGENS_PARAMETER, vuilniswagens)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
