package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Verzoek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class VerzoekRepositoryWithBagRelationshipsImpl implements VerzoekRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String VERZOEKS_PARAMETER = "verzoeks";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Verzoek> fetchBagRelationships(Optional<Verzoek> verzoek) {
        return verzoek
            .map(this::fetchBetreftProjectactiviteits)
            .map(this::fetchBetreftProjectlocaties)
            .map(this::fetchBetreftActiviteits)
            .map(this::fetchBetreftLocaties);
    }

    @Override
    public Page<Verzoek> fetchBagRelationships(Page<Verzoek> verzoeks) {
        return new PageImpl<>(fetchBagRelationships(verzoeks.getContent()), verzoeks.getPageable(), verzoeks.getTotalElements());
    }

    @Override
    public List<Verzoek> fetchBagRelationships(List<Verzoek> verzoeks) {
        return Optional.of(verzoeks)
            .map(this::fetchBetreftProjectactiviteits)
            .map(this::fetchBetreftProjectlocaties)
            .map(this::fetchBetreftActiviteits)
            .map(this::fetchBetreftLocaties)
            .orElse(Collections.emptyList());
    }

    Verzoek fetchBetreftProjectactiviteits(Verzoek result) {
        return entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftProjectactiviteits where verzoek.id = :id",
                Verzoek.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Verzoek> fetchBetreftProjectactiviteits(List<Verzoek> verzoeks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, verzoeks.size()).forEach(index -> order.put(verzoeks.get(index).getId(), index));
        List<Verzoek> result = entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftProjectactiviteits where verzoek in :verzoeks",
                Verzoek.class
            )
            .setParameter(VERZOEKS_PARAMETER, verzoeks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Verzoek fetchBetreftProjectlocaties(Verzoek result) {
        return entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftProjectlocaties where verzoek.id = :id",
                Verzoek.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Verzoek> fetchBetreftProjectlocaties(List<Verzoek> verzoeks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, verzoeks.size()).forEach(index -> order.put(verzoeks.get(index).getId(), index));
        List<Verzoek> result = entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftProjectlocaties where verzoek in :verzoeks",
                Verzoek.class
            )
            .setParameter(VERZOEKS_PARAMETER, verzoeks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Verzoek fetchBetreftActiviteits(Verzoek result) {
        return entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftActiviteits where verzoek.id = :id",
                Verzoek.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Verzoek> fetchBetreftActiviteits(List<Verzoek> verzoeks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, verzoeks.size()).forEach(index -> order.put(verzoeks.get(index).getId(), index));
        List<Verzoek> result = entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftActiviteits where verzoek in :verzoeks",
                Verzoek.class
            )
            .setParameter(VERZOEKS_PARAMETER, verzoeks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Verzoek fetchBetreftLocaties(Verzoek result) {
        return entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftLocaties where verzoek.id = :id",
                Verzoek.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Verzoek> fetchBetreftLocaties(List<Verzoek> verzoeks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, verzoeks.size()).forEach(index -> order.put(verzoeks.get(index).getId(), index));
        List<Verzoek> result = entityManager
            .createQuery(
                "select verzoek from Verzoek verzoek left join fetch verzoek.betreftLocaties where verzoek in :verzoeks",
                Verzoek.class
            )
            .setParameter(VERZOEKS_PARAMETER, verzoeks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
