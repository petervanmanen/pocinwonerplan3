package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Mailing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MailingRepositoryWithBagRelationshipsImpl implements MailingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MAILINGS_PARAMETER = "mailings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Mailing> fetchBagRelationships(Optional<Mailing> mailing) {
        return mailing.map(this::fetchVersturenaanMuseumrelaties);
    }

    @Override
    public Page<Mailing> fetchBagRelationships(Page<Mailing> mailings) {
        return new PageImpl<>(fetchBagRelationships(mailings.getContent()), mailings.getPageable(), mailings.getTotalElements());
    }

    @Override
    public List<Mailing> fetchBagRelationships(List<Mailing> mailings) {
        return Optional.of(mailings).map(this::fetchVersturenaanMuseumrelaties).orElse(Collections.emptyList());
    }

    Mailing fetchVersturenaanMuseumrelaties(Mailing result) {
        return entityManager
            .createQuery(
                "select mailing from Mailing mailing left join fetch mailing.versturenaanMuseumrelaties where mailing.id = :id",
                Mailing.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Mailing> fetchVersturenaanMuseumrelaties(List<Mailing> mailings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, mailings.size()).forEach(index -> order.put(mailings.get(index).getId(), index));
        List<Mailing> result = entityManager
            .createQuery(
                "select mailing from Mailing mailing left join fetch mailing.versturenaanMuseumrelaties where mailing in :mailings",
                Mailing.class
            )
            .setParameter(MAILINGS_PARAMETER, mailings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
