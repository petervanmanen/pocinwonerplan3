package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Milieustraat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MilieustraatRepositoryWithBagRelationshipsImpl implements MilieustraatRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MILIEUSTRAATS_PARAMETER = "milieustraats";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Milieustraat> fetchBagRelationships(Optional<Milieustraat> milieustraat) {
        return milieustraat.map(this::fetchInzamelpuntvanFracties);
    }

    @Override
    public Page<Milieustraat> fetchBagRelationships(Page<Milieustraat> milieustraats) {
        return new PageImpl<>(
            fetchBagRelationships(milieustraats.getContent()),
            milieustraats.getPageable(),
            milieustraats.getTotalElements()
        );
    }

    @Override
    public List<Milieustraat> fetchBagRelationships(List<Milieustraat> milieustraats) {
        return Optional.of(milieustraats).map(this::fetchInzamelpuntvanFracties).orElse(Collections.emptyList());
    }

    Milieustraat fetchInzamelpuntvanFracties(Milieustraat result) {
        return entityManager
            .createQuery(
                "select milieustraat from Milieustraat milieustraat left join fetch milieustraat.inzamelpuntvanFracties where milieustraat.id = :id",
                Milieustraat.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Milieustraat> fetchInzamelpuntvanFracties(List<Milieustraat> milieustraats) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, milieustraats.size()).forEach(index -> order.put(milieustraats.get(index).getId(), index));
        List<Milieustraat> result = entityManager
            .createQuery(
                "select milieustraat from Milieustraat milieustraat left join fetch milieustraat.inzamelpuntvanFracties where milieustraat in :milieustraats",
                Milieustraat.class
            )
            .setParameter(MILIEUSTRAATS_PARAMETER, milieustraats)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
