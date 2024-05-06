package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Archief;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ArchiefRepositoryWithBagRelationshipsImpl implements ArchiefRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ARCHIEFS_PARAMETER = "archiefs";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Archief> fetchBagRelationships(Optional<Archief> archief) {
        return archief.map(this::fetchValtbinnenArchiefcategories).map(this::fetchStamtuitPeriodes);
    }

    @Override
    public Page<Archief> fetchBagRelationships(Page<Archief> archiefs) {
        return new PageImpl<>(fetchBagRelationships(archiefs.getContent()), archiefs.getPageable(), archiefs.getTotalElements());
    }

    @Override
    public List<Archief> fetchBagRelationships(List<Archief> archiefs) {
        return Optional.of(archiefs)
            .map(this::fetchValtbinnenArchiefcategories)
            .map(this::fetchStamtuitPeriodes)
            .orElse(Collections.emptyList());
    }

    Archief fetchValtbinnenArchiefcategories(Archief result) {
        return entityManager
            .createQuery(
                "select archief from Archief archief left join fetch archief.valtbinnenArchiefcategories where archief.id = :id",
                Archief.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Archief> fetchValtbinnenArchiefcategories(List<Archief> archiefs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, archiefs.size()).forEach(index -> order.put(archiefs.get(index).getId(), index));
        List<Archief> result = entityManager
            .createQuery(
                "select archief from Archief archief left join fetch archief.valtbinnenArchiefcategories where archief in :archiefs",
                Archief.class
            )
            .setParameter(ARCHIEFS_PARAMETER, archiefs)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Archief fetchStamtuitPeriodes(Archief result) {
        return entityManager
            .createQuery(
                "select archief from Archief archief left join fetch archief.stamtuitPeriodes where archief.id = :id",
                Archief.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Archief> fetchStamtuitPeriodes(List<Archief> archiefs) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, archiefs.size()).forEach(index -> order.put(archiefs.get(index).getId(), index));
        List<Archief> result = entityManager
            .createQuery(
                "select archief from Archief archief left join fetch archief.stamtuitPeriodes where archief in :archiefs",
                Archief.class
            )
            .setParameter(ARCHIEFS_PARAMETER, archiefs)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
