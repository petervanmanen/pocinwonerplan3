package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Archiefstuk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ArchiefstukRepositoryWithBagRelationshipsImpl implements ArchiefstukRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ARCHIEFSTUKS_PARAMETER = "archiefstuks";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Archiefstuk> fetchBagRelationships(Optional<Archiefstuk> archiefstuk) {
        return archiefstuk.map(this::fetchHeeftOrdeningsschemas).map(this::fetchStamtuitPeriodes);
    }

    @Override
    public Page<Archiefstuk> fetchBagRelationships(Page<Archiefstuk> archiefstuks) {
        return new PageImpl<>(
            fetchBagRelationships(archiefstuks.getContent()),
            archiefstuks.getPageable(),
            archiefstuks.getTotalElements()
        );
    }

    @Override
    public List<Archiefstuk> fetchBagRelationships(List<Archiefstuk> archiefstuks) {
        return Optional.of(archiefstuks)
            .map(this::fetchHeeftOrdeningsschemas)
            .map(this::fetchStamtuitPeriodes)
            .orElse(Collections.emptyList());
    }

    Archiefstuk fetchHeeftOrdeningsschemas(Archiefstuk result) {
        return entityManager
            .createQuery(
                "select archiefstuk from Archiefstuk archiefstuk left join fetch archiefstuk.heeftOrdeningsschemas where archiefstuk.id = :id",
                Archiefstuk.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Archiefstuk> fetchHeeftOrdeningsschemas(List<Archiefstuk> archiefstuks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, archiefstuks.size()).forEach(index -> order.put(archiefstuks.get(index).getId(), index));
        List<Archiefstuk> result = entityManager
            .createQuery(
                "select archiefstuk from Archiefstuk archiefstuk left join fetch archiefstuk.heeftOrdeningsschemas where archiefstuk in :archiefstuks",
                Archiefstuk.class
            )
            .setParameter(ARCHIEFSTUKS_PARAMETER, archiefstuks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Archiefstuk fetchStamtuitPeriodes(Archiefstuk result) {
        return entityManager
            .createQuery(
                "select archiefstuk from Archiefstuk archiefstuk left join fetch archiefstuk.stamtuitPeriodes where archiefstuk.id = :id",
                Archiefstuk.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Archiefstuk> fetchStamtuitPeriodes(List<Archiefstuk> archiefstuks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, archiefstuks.size()).forEach(index -> order.put(archiefstuks.get(index).getId(), index));
        List<Archiefstuk> result = entityManager
            .createQuery(
                "select archiefstuk from Archiefstuk archiefstuk left join fetch archiefstuk.stamtuitPeriodes where archiefstuk in :archiefstuks",
                Archiefstuk.class
            )
            .setParameter(ARCHIEFSTUKS_PARAMETER, archiefstuks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
