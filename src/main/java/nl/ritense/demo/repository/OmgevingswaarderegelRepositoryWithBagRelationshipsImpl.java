package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Omgevingswaarderegel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class OmgevingswaarderegelRepositoryWithBagRelationshipsImpl implements OmgevingswaarderegelRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String OMGEVINGSWAARDEREGELS_PARAMETER = "omgevingswaarderegels";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Omgevingswaarderegel> fetchBagRelationships(Optional<Omgevingswaarderegel> omgevingswaarderegel) {
        return omgevingswaarderegel.map(this::fetchBeschrijftOmgevingsnorms).map(this::fetchBeschrijftOmgevingswaardes);
    }

    @Override
    public Page<Omgevingswaarderegel> fetchBagRelationships(Page<Omgevingswaarderegel> omgevingswaarderegels) {
        return new PageImpl<>(
            fetchBagRelationships(omgevingswaarderegels.getContent()),
            omgevingswaarderegels.getPageable(),
            omgevingswaarderegels.getTotalElements()
        );
    }

    @Override
    public List<Omgevingswaarderegel> fetchBagRelationships(List<Omgevingswaarderegel> omgevingswaarderegels) {
        return Optional.of(omgevingswaarderegels)
            .map(this::fetchBeschrijftOmgevingsnorms)
            .map(this::fetchBeschrijftOmgevingswaardes)
            .orElse(Collections.emptyList());
    }

    Omgevingswaarderegel fetchBeschrijftOmgevingsnorms(Omgevingswaarderegel result) {
        return entityManager
            .createQuery(
                "select omgevingswaarderegel from Omgevingswaarderegel omgevingswaarderegel left join fetch omgevingswaarderegel.beschrijftOmgevingsnorms where omgevingswaarderegel.id = :id",
                Omgevingswaarderegel.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Omgevingswaarderegel> fetchBeschrijftOmgevingsnorms(List<Omgevingswaarderegel> omgevingswaarderegels) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, omgevingswaarderegels.size()).forEach(index -> order.put(omgevingswaarderegels.get(index).getId(), index));
        List<Omgevingswaarderegel> result = entityManager
            .createQuery(
                "select omgevingswaarderegel from Omgevingswaarderegel omgevingswaarderegel left join fetch omgevingswaarderegel.beschrijftOmgevingsnorms where omgevingswaarderegel in :omgevingswaarderegels",
                Omgevingswaarderegel.class
            )
            .setParameter(OMGEVINGSWAARDEREGELS_PARAMETER, omgevingswaarderegels)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Omgevingswaarderegel fetchBeschrijftOmgevingswaardes(Omgevingswaarderegel result) {
        return entityManager
            .createQuery(
                "select omgevingswaarderegel from Omgevingswaarderegel omgevingswaarderegel left join fetch omgevingswaarderegel.beschrijftOmgevingswaardes where omgevingswaarderegel.id = :id",
                Omgevingswaarderegel.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Omgevingswaarderegel> fetchBeschrijftOmgevingswaardes(List<Omgevingswaarderegel> omgevingswaarderegels) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, omgevingswaarderegels.size()).forEach(index -> order.put(omgevingswaarderegels.get(index).getId(), index));
        List<Omgevingswaarderegel> result = entityManager
            .createQuery(
                "select omgevingswaarderegel from Omgevingswaarderegel omgevingswaarderegel left join fetch omgevingswaarderegel.beschrijftOmgevingswaardes where omgevingswaarderegel in :omgevingswaarderegels",
                Omgevingswaarderegel.class
            )
            .setParameter(OMGEVINGSWAARDEREGELS_PARAMETER, omgevingswaarderegels)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
