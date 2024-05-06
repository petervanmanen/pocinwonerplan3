package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Regeltekst;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class RegeltekstRepositoryWithBagRelationshipsImpl implements RegeltekstRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String REGELTEKSTS_PARAMETER = "regelteksts";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Regeltekst> fetchBagRelationships(Optional<Regeltekst> regeltekst) {
        return regeltekst
            .map(this::fetchHeeftthemaThemas)
            .map(this::fetchHeeftidealisatieIdealisaties)
            .map(this::fetchWerkingsgebiedLocaties);
    }

    @Override
    public Page<Regeltekst> fetchBagRelationships(Page<Regeltekst> regelteksts) {
        return new PageImpl<>(fetchBagRelationships(regelteksts.getContent()), regelteksts.getPageable(), regelteksts.getTotalElements());
    }

    @Override
    public List<Regeltekst> fetchBagRelationships(List<Regeltekst> regelteksts) {
        return Optional.of(regelteksts)
            .map(this::fetchHeeftthemaThemas)
            .map(this::fetchHeeftidealisatieIdealisaties)
            .map(this::fetchWerkingsgebiedLocaties)
            .orElse(Collections.emptyList());
    }

    Regeltekst fetchHeeftthemaThemas(Regeltekst result) {
        return entityManager
            .createQuery(
                "select regeltekst from Regeltekst regeltekst left join fetch regeltekst.heeftthemaThemas where regeltekst.id = :id",
                Regeltekst.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Regeltekst> fetchHeeftthemaThemas(List<Regeltekst> regelteksts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, regelteksts.size()).forEach(index -> order.put(regelteksts.get(index).getId(), index));
        List<Regeltekst> result = entityManager
            .createQuery(
                "select regeltekst from Regeltekst regeltekst left join fetch regeltekst.heeftthemaThemas where regeltekst in :regelteksts",
                Regeltekst.class
            )
            .setParameter(REGELTEKSTS_PARAMETER, regelteksts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Regeltekst fetchHeeftidealisatieIdealisaties(Regeltekst result) {
        return entityManager
            .createQuery(
                "select regeltekst from Regeltekst regeltekst left join fetch regeltekst.heeftidealisatieIdealisaties where regeltekst.id = :id",
                Regeltekst.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Regeltekst> fetchHeeftidealisatieIdealisaties(List<Regeltekst> regelteksts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, regelteksts.size()).forEach(index -> order.put(regelteksts.get(index).getId(), index));
        List<Regeltekst> result = entityManager
            .createQuery(
                "select regeltekst from Regeltekst regeltekst left join fetch regeltekst.heeftidealisatieIdealisaties where regeltekst in :regelteksts",
                Regeltekst.class
            )
            .setParameter(REGELTEKSTS_PARAMETER, regelteksts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Regeltekst fetchWerkingsgebiedLocaties(Regeltekst result) {
        return entityManager
            .createQuery(
                "select regeltekst from Regeltekst regeltekst left join fetch regeltekst.werkingsgebiedLocaties where regeltekst.id = :id",
                Regeltekst.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Regeltekst> fetchWerkingsgebiedLocaties(List<Regeltekst> regelteksts) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, regelteksts.size()).forEach(index -> order.put(regelteksts.get(index).getId(), index));
        List<Regeltekst> result = entityManager
            .createQuery(
                "select regeltekst from Regeltekst regeltekst left join fetch regeltekst.werkingsgebiedLocaties where regeltekst in :regelteksts",
                Regeltekst.class
            )
            .setParameter(REGELTEKSTS_PARAMETER, regelteksts)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
