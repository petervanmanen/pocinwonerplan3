package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Sollicitatiegesprek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SollicitatiegesprekRepositoryWithBagRelationshipsImpl implements SollicitatiegesprekRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SOLLICITATIEGESPREKS_PARAMETER = "sollicitatiegespreks";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Sollicitatiegesprek> fetchBagRelationships(Optional<Sollicitatiegesprek> sollicitatiegesprek) {
        return sollicitatiegesprek.map(this::fetchKandidaatSollicitants).map(this::fetchDoetsollicitatiegesprekWerknemers);
    }

    @Override
    public Page<Sollicitatiegesprek> fetchBagRelationships(Page<Sollicitatiegesprek> sollicitatiegespreks) {
        return new PageImpl<>(
            fetchBagRelationships(sollicitatiegespreks.getContent()),
            sollicitatiegespreks.getPageable(),
            sollicitatiegespreks.getTotalElements()
        );
    }

    @Override
    public List<Sollicitatiegesprek> fetchBagRelationships(List<Sollicitatiegesprek> sollicitatiegespreks) {
        return Optional.of(sollicitatiegespreks)
            .map(this::fetchKandidaatSollicitants)
            .map(this::fetchDoetsollicitatiegesprekWerknemers)
            .orElse(Collections.emptyList());
    }

    Sollicitatiegesprek fetchKandidaatSollicitants(Sollicitatiegesprek result) {
        return entityManager
            .createQuery(
                "select sollicitatiegesprek from Sollicitatiegesprek sollicitatiegesprek left join fetch sollicitatiegesprek.kandidaatSollicitants where sollicitatiegesprek.id = :id",
                Sollicitatiegesprek.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Sollicitatiegesprek> fetchKandidaatSollicitants(List<Sollicitatiegesprek> sollicitatiegespreks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, sollicitatiegespreks.size()).forEach(index -> order.put(sollicitatiegespreks.get(index).getId(), index));
        List<Sollicitatiegesprek> result = entityManager
            .createQuery(
                "select sollicitatiegesprek from Sollicitatiegesprek sollicitatiegesprek left join fetch sollicitatiegesprek.kandidaatSollicitants where sollicitatiegesprek in :sollicitatiegespreks",
                Sollicitatiegesprek.class
            )
            .setParameter(SOLLICITATIEGESPREKS_PARAMETER, sollicitatiegespreks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Sollicitatiegesprek fetchDoetsollicitatiegesprekWerknemers(Sollicitatiegesprek result) {
        return entityManager
            .createQuery(
                "select sollicitatiegesprek from Sollicitatiegesprek sollicitatiegesprek left join fetch sollicitatiegesprek.doetsollicitatiegesprekWerknemers where sollicitatiegesprek.id = :id",
                Sollicitatiegesprek.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Sollicitatiegesprek> fetchDoetsollicitatiegesprekWerknemers(List<Sollicitatiegesprek> sollicitatiegespreks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, sollicitatiegespreks.size()).forEach(index -> order.put(sollicitatiegespreks.get(index).getId(), index));
        List<Sollicitatiegesprek> result = entityManager
            .createQuery(
                "select sollicitatiegesprek from Sollicitatiegesprek sollicitatiegesprek left join fetch sollicitatiegesprek.doetsollicitatiegesprekWerknemers where sollicitatiegesprek in :sollicitatiegespreks",
                Sollicitatiegesprek.class
            )
            .setParameter(SOLLICITATIEGESPREKS_PARAMETER, sollicitatiegespreks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
