package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Werkbon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WerkbonRepositoryWithBagRelationshipsImpl implements WerkbonRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String WERKBONS_PARAMETER = "werkbons";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Werkbon> fetchBagRelationships(Optional<Werkbon> werkbon) {
        return werkbon.map(this::fetchBetreftBouwdeels).map(this::fetchBetreftBouwdeelelements);
    }

    @Override
    public Page<Werkbon> fetchBagRelationships(Page<Werkbon> werkbons) {
        return new PageImpl<>(fetchBagRelationships(werkbons.getContent()), werkbons.getPageable(), werkbons.getTotalElements());
    }

    @Override
    public List<Werkbon> fetchBagRelationships(List<Werkbon> werkbons) {
        return Optional.of(werkbons)
            .map(this::fetchBetreftBouwdeels)
            .map(this::fetchBetreftBouwdeelelements)
            .orElse(Collections.emptyList());
    }

    Werkbon fetchBetreftBouwdeels(Werkbon result) {
        return entityManager
            .createQuery(
                "select werkbon from Werkbon werkbon left join fetch werkbon.betreftBouwdeels where werkbon.id = :id",
                Werkbon.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Werkbon> fetchBetreftBouwdeels(List<Werkbon> werkbons) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, werkbons.size()).forEach(index -> order.put(werkbons.get(index).getId(), index));
        List<Werkbon> result = entityManager
            .createQuery(
                "select werkbon from Werkbon werkbon left join fetch werkbon.betreftBouwdeels where werkbon in :werkbons",
                Werkbon.class
            )
            .setParameter(WERKBONS_PARAMETER, werkbons)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Werkbon fetchBetreftBouwdeelelements(Werkbon result) {
        return entityManager
            .createQuery(
                "select werkbon from Werkbon werkbon left join fetch werkbon.betreftBouwdeelelements where werkbon.id = :id",
                Werkbon.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Werkbon> fetchBetreftBouwdeelelements(List<Werkbon> werkbons) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, werkbons.size()).forEach(index -> order.put(werkbons.get(index).getId(), index));
        List<Werkbon> result = entityManager
            .createQuery(
                "select werkbon from Werkbon werkbon left join fetch werkbon.betreftBouwdeelelements where werkbon in :werkbons",
                Werkbon.class
            )
            .setParameter(WERKBONS_PARAMETER, werkbons)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
