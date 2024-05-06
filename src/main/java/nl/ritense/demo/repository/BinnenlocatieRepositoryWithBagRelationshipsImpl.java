package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Binnenlocatie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BinnenlocatieRepositoryWithBagRelationshipsImpl implements BinnenlocatieRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String BINNENLOCATIES_PARAMETER = "binnenlocaties";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Binnenlocatie> fetchBagRelationships(Optional<Binnenlocatie> binnenlocatie) {
        return binnenlocatie.map(this::fetchHeeftBelijnings).map(this::fetchHeeftSportmateriaals);
    }

    @Override
    public Page<Binnenlocatie> fetchBagRelationships(Page<Binnenlocatie> binnenlocaties) {
        return new PageImpl<>(
            fetchBagRelationships(binnenlocaties.getContent()),
            binnenlocaties.getPageable(),
            binnenlocaties.getTotalElements()
        );
    }

    @Override
    public List<Binnenlocatie> fetchBagRelationships(List<Binnenlocatie> binnenlocaties) {
        return Optional.of(binnenlocaties)
            .map(this::fetchHeeftBelijnings)
            .map(this::fetchHeeftSportmateriaals)
            .orElse(Collections.emptyList());
    }

    Binnenlocatie fetchHeeftBelijnings(Binnenlocatie result) {
        return entityManager
            .createQuery(
                "select binnenlocatie from Binnenlocatie binnenlocatie left join fetch binnenlocatie.heeftBelijnings where binnenlocatie.id = :id",
                Binnenlocatie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Binnenlocatie> fetchHeeftBelijnings(List<Binnenlocatie> binnenlocaties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, binnenlocaties.size()).forEach(index -> order.put(binnenlocaties.get(index).getId(), index));
        List<Binnenlocatie> result = entityManager
            .createQuery(
                "select binnenlocatie from Binnenlocatie binnenlocatie left join fetch binnenlocatie.heeftBelijnings where binnenlocatie in :binnenlocaties",
                Binnenlocatie.class
            )
            .setParameter(BINNENLOCATIES_PARAMETER, binnenlocaties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Binnenlocatie fetchHeeftSportmateriaals(Binnenlocatie result) {
        return entityManager
            .createQuery(
                "select binnenlocatie from Binnenlocatie binnenlocatie left join fetch binnenlocatie.heeftSportmateriaals where binnenlocatie.id = :id",
                Binnenlocatie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Binnenlocatie> fetchHeeftSportmateriaals(List<Binnenlocatie> binnenlocaties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, binnenlocaties.size()).forEach(index -> order.put(binnenlocaties.get(index).getId(), index));
        List<Binnenlocatie> result = entityManager
            .createQuery(
                "select binnenlocatie from Binnenlocatie binnenlocatie left join fetch binnenlocatie.heeftSportmateriaals where binnenlocatie in :binnenlocaties",
                Binnenlocatie.class
            )
            .setParameter(BINNENLOCATIES_PARAMETER, binnenlocaties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
