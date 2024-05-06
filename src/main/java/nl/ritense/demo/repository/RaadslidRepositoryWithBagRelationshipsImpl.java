package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Raadslid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RaadslidRepositoryWithBagRelationshipsImpl implements RaadslidRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String RAADSLIDS_PARAMETER = "raadslids";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Raadslid> fetchBagRelationships(Optional<Raadslid> raadslid) {
        return raadslid.map(this::fetchIslidvanRaadscommissies);
    }

    @Override
    public Page<Raadslid> fetchBagRelationships(Page<Raadslid> raadslids) {
        return new PageImpl<>(fetchBagRelationships(raadslids.getContent()), raadslids.getPageable(), raadslids.getTotalElements());
    }

    @Override
    public List<Raadslid> fetchBagRelationships(List<Raadslid> raadslids) {
        return Optional.of(raadslids).map(this::fetchIslidvanRaadscommissies).orElse(Collections.emptyList());
    }

    Raadslid fetchIslidvanRaadscommissies(Raadslid result) {
        return entityManager
            .createQuery(
                "select raadslid from Raadslid raadslid left join fetch raadslid.islidvanRaadscommissies where raadslid.id = :id",
                Raadslid.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Raadslid> fetchIslidvanRaadscommissies(List<Raadslid> raadslids) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, raadslids.size()).forEach(index -> order.put(raadslids.get(index).getId(), index));
        List<Raadslid> result = entityManager
            .createQuery(
                "select raadslid from Raadslid raadslid left join fetch raadslid.islidvanRaadscommissies where raadslid in :raadslids",
                Raadslid.class
            )
            .setParameter(RAADSLIDS_PARAMETER, raadslids)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
