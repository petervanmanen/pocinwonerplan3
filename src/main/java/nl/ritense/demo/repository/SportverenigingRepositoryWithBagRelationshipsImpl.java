package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Sportvereniging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SportverenigingRepositoryWithBagRelationshipsImpl implements SportverenigingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SPORTVERENIGINGS_PARAMETER = "sportverenigings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Sportvereniging> fetchBagRelationships(Optional<Sportvereniging> sportvereniging) {
        return sportvereniging.map(this::fetchOefentuitSports).map(this::fetchGebruiktSportlocaties);
    }

    @Override
    public Page<Sportvereniging> fetchBagRelationships(Page<Sportvereniging> sportverenigings) {
        return new PageImpl<>(
            fetchBagRelationships(sportverenigings.getContent()),
            sportverenigings.getPageable(),
            sportverenigings.getTotalElements()
        );
    }

    @Override
    public List<Sportvereniging> fetchBagRelationships(List<Sportvereniging> sportverenigings) {
        return Optional.of(sportverenigings)
            .map(this::fetchOefentuitSports)
            .map(this::fetchGebruiktSportlocaties)
            .orElse(Collections.emptyList());
    }

    Sportvereniging fetchOefentuitSports(Sportvereniging result) {
        return entityManager
            .createQuery(
                "select sportvereniging from Sportvereniging sportvereniging left join fetch sportvereniging.oefentuitSports where sportvereniging.id = :id",
                Sportvereniging.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Sportvereniging> fetchOefentuitSports(List<Sportvereniging> sportverenigings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, sportverenigings.size()).forEach(index -> order.put(sportverenigings.get(index).getId(), index));
        List<Sportvereniging> result = entityManager
            .createQuery(
                "select sportvereniging from Sportvereniging sportvereniging left join fetch sportvereniging.oefentuitSports where sportvereniging in :sportverenigings",
                Sportvereniging.class
            )
            .setParameter(SPORTVERENIGINGS_PARAMETER, sportverenigings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Sportvereniging fetchGebruiktSportlocaties(Sportvereniging result) {
        return entityManager
            .createQuery(
                "select sportvereniging from Sportvereniging sportvereniging left join fetch sportvereniging.gebruiktSportlocaties where sportvereniging.id = :id",
                Sportvereniging.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Sportvereniging> fetchGebruiktSportlocaties(List<Sportvereniging> sportverenigings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, sportverenigings.size()).forEach(index -> order.put(sportverenigings.get(index).getId(), index));
        List<Sportvereniging> result = entityManager
            .createQuery(
                "select sportvereniging from Sportvereniging sportvereniging left join fetch sportvereniging.gebruiktSportlocaties where sportvereniging in :sportverenigings",
                Sportvereniging.class
            )
            .setParameter(SPORTVERENIGINGS_PARAMETER, sportverenigings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
