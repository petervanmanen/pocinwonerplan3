package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Raadsstuk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RaadsstukRepositoryWithBagRelationshipsImpl implements RaadsstukRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String RAADSSTUKS_PARAMETER = "raadsstuks";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Raadsstuk> fetchBagRelationships(Optional<Raadsstuk> raadsstuk) {
        return raadsstuk
            .map(this::fetchBehandeltAgendapunts)
            .map(this::fetchHoortbijProgrammas)
            .map(this::fetchWordtbehandeldinVergaderings);
    }

    @Override
    public Page<Raadsstuk> fetchBagRelationships(Page<Raadsstuk> raadsstuks) {
        return new PageImpl<>(fetchBagRelationships(raadsstuks.getContent()), raadsstuks.getPageable(), raadsstuks.getTotalElements());
    }

    @Override
    public List<Raadsstuk> fetchBagRelationships(List<Raadsstuk> raadsstuks) {
        return Optional.of(raadsstuks)
            .map(this::fetchBehandeltAgendapunts)
            .map(this::fetchHoortbijProgrammas)
            .map(this::fetchWordtbehandeldinVergaderings)
            .orElse(Collections.emptyList());
    }

    Raadsstuk fetchBehandeltAgendapunts(Raadsstuk result) {
        return entityManager
            .createQuery(
                "select raadsstuk from Raadsstuk raadsstuk left join fetch raadsstuk.behandeltAgendapunts where raadsstuk.id = :id",
                Raadsstuk.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Raadsstuk> fetchBehandeltAgendapunts(List<Raadsstuk> raadsstuks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, raadsstuks.size()).forEach(index -> order.put(raadsstuks.get(index).getId(), index));
        List<Raadsstuk> result = entityManager
            .createQuery(
                "select raadsstuk from Raadsstuk raadsstuk left join fetch raadsstuk.behandeltAgendapunts where raadsstuk in :raadsstuks",
                Raadsstuk.class
            )
            .setParameter(RAADSSTUKS_PARAMETER, raadsstuks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Raadsstuk fetchHoortbijProgrammas(Raadsstuk result) {
        return entityManager
            .createQuery(
                "select raadsstuk from Raadsstuk raadsstuk left join fetch raadsstuk.hoortbijProgrammas where raadsstuk.id = :id",
                Raadsstuk.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Raadsstuk> fetchHoortbijProgrammas(List<Raadsstuk> raadsstuks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, raadsstuks.size()).forEach(index -> order.put(raadsstuks.get(index).getId(), index));
        List<Raadsstuk> result = entityManager
            .createQuery(
                "select raadsstuk from Raadsstuk raadsstuk left join fetch raadsstuk.hoortbijProgrammas where raadsstuk in :raadsstuks",
                Raadsstuk.class
            )
            .setParameter(RAADSSTUKS_PARAMETER, raadsstuks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Raadsstuk fetchWordtbehandeldinVergaderings(Raadsstuk result) {
        return entityManager
            .createQuery(
                "select raadsstuk from Raadsstuk raadsstuk left join fetch raadsstuk.wordtbehandeldinVergaderings where raadsstuk.id = :id",
                Raadsstuk.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Raadsstuk> fetchWordtbehandeldinVergaderings(List<Raadsstuk> raadsstuks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, raadsstuks.size()).forEach(index -> order.put(raadsstuks.get(index).getId(), index));
        List<Raadsstuk> result = entityManager
            .createQuery(
                "select raadsstuk from Raadsstuk raadsstuk left join fetch raadsstuk.wordtbehandeldinVergaderings where raadsstuk in :raadsstuks",
                Raadsstuk.class
            )
            .setParameter(RAADSSTUKS_PARAMETER, raadsstuks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
