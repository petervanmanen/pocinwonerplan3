package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Nummeraanduiding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class NummeraanduidingRepositoryWithBagRelationshipsImpl implements NummeraanduidingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String NUMMERAANDUIDINGS_PARAMETER = "nummeraanduidings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Nummeraanduiding> fetchBagRelationships(Optional<Nummeraanduiding> nummeraanduiding) {
        return nummeraanduiding.map(this::fetchLigtinGebieds);
    }

    @Override
    public Page<Nummeraanduiding> fetchBagRelationships(Page<Nummeraanduiding> nummeraanduidings) {
        return new PageImpl<>(
            fetchBagRelationships(nummeraanduidings.getContent()),
            nummeraanduidings.getPageable(),
            nummeraanduidings.getTotalElements()
        );
    }

    @Override
    public List<Nummeraanduiding> fetchBagRelationships(List<Nummeraanduiding> nummeraanduidings) {
        return Optional.of(nummeraanduidings).map(this::fetchLigtinGebieds).orElse(Collections.emptyList());
    }

    Nummeraanduiding fetchLigtinGebieds(Nummeraanduiding result) {
        return entityManager
            .createQuery(
                "select nummeraanduiding from Nummeraanduiding nummeraanduiding left join fetch nummeraanduiding.ligtinGebieds where nummeraanduiding.id = :id",
                Nummeraanduiding.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Nummeraanduiding> fetchLigtinGebieds(List<Nummeraanduiding> nummeraanduidings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, nummeraanduidings.size()).forEach(index -> order.put(nummeraanduidings.get(index).getId(), index));
        List<Nummeraanduiding> result = entityManager
            .createQuery(
                "select nummeraanduiding from Nummeraanduiding nummeraanduiding left join fetch nummeraanduiding.ligtinGebieds where nummeraanduiding in :nummeraanduidings",
                Nummeraanduiding.class
            )
            .setParameter(NUMMERAANDUIDINGS_PARAMETER, nummeraanduidings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
