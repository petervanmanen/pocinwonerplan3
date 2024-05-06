package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Rechtspersoon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RechtspersoonRepositoryWithBagRelationshipsImpl implements RechtspersoonRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String RECHTSPERSOONS_PARAMETER = "rechtspersoons";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Rechtspersoon> fetchBagRelationships(Optional<Rechtspersoon> rechtspersoon) {
        return rechtspersoon.map(this::fetchBetrokkenenKadastralemutaties);
    }

    @Override
    public Page<Rechtspersoon> fetchBagRelationships(Page<Rechtspersoon> rechtspersoons) {
        return new PageImpl<>(
            fetchBagRelationships(rechtspersoons.getContent()),
            rechtspersoons.getPageable(),
            rechtspersoons.getTotalElements()
        );
    }

    @Override
    public List<Rechtspersoon> fetchBagRelationships(List<Rechtspersoon> rechtspersoons) {
        return Optional.of(rechtspersoons).map(this::fetchBetrokkenenKadastralemutaties).orElse(Collections.emptyList());
    }

    Rechtspersoon fetchBetrokkenenKadastralemutaties(Rechtspersoon result) {
        return entityManager
            .createQuery(
                "select rechtspersoon from Rechtspersoon rechtspersoon left join fetch rechtspersoon.betrokkenenKadastralemutaties where rechtspersoon.id = :id",
                Rechtspersoon.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Rechtspersoon> fetchBetrokkenenKadastralemutaties(List<Rechtspersoon> rechtspersoons) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, rechtspersoons.size()).forEach(index -> order.put(rechtspersoons.get(index).getId(), index));
        List<Rechtspersoon> result = entityManager
            .createQuery(
                "select rechtspersoon from Rechtspersoon rechtspersoon left join fetch rechtspersoon.betrokkenenKadastralemutaties where rechtspersoon in :rechtspersoons",
                Rechtspersoon.class
            )
            .setParameter(RECHTSPERSOONS_PARAMETER, rechtspersoons)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
