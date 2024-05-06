package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Bedrijfsproces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BedrijfsprocesRepositoryWithBagRelationshipsImpl implements BedrijfsprocesRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String BEDRIJFSPROCES_PARAMETER = "bedrijfsproces";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Bedrijfsproces> fetchBagRelationships(Optional<Bedrijfsproces> bedrijfsproces) {
        return bedrijfsproces.map(this::fetchUitgevoerdbinnenZaaks);
    }

    @Override
    public Page<Bedrijfsproces> fetchBagRelationships(Page<Bedrijfsproces> bedrijfsproces) {
        return new PageImpl<>(
            fetchBagRelationships(bedrijfsproces.getContent()),
            bedrijfsproces.getPageable(),
            bedrijfsproces.getTotalElements()
        );
    }

    @Override
    public List<Bedrijfsproces> fetchBagRelationships(List<Bedrijfsproces> bedrijfsproces) {
        return Optional.of(bedrijfsproces).map(this::fetchUitgevoerdbinnenZaaks).orElse(Collections.emptyList());
    }

    Bedrijfsproces fetchUitgevoerdbinnenZaaks(Bedrijfsproces result) {
        return entityManager
            .createQuery(
                "select bedrijfsproces from Bedrijfsproces bedrijfsproces left join fetch bedrijfsproces.uitgevoerdbinnenZaaks where bedrijfsproces.id = :id",
                Bedrijfsproces.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Bedrijfsproces> fetchUitgevoerdbinnenZaaks(List<Bedrijfsproces> bedrijfsproces) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, bedrijfsproces.size()).forEach(index -> order.put(bedrijfsproces.get(index).getId(), index));
        List<Bedrijfsproces> result = entityManager
            .createQuery(
                "select bedrijfsproces from Bedrijfsproces bedrijfsproces left join fetch bedrijfsproces.uitgevoerdbinnenZaaks where bedrijfsproces in :bedrijfsproces",
                Bedrijfsproces.class
            )
            .setParameter(BEDRIJFSPROCES_PARAMETER, bedrijfsproces)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
