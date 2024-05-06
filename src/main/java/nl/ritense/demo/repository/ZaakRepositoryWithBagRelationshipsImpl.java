package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Zaak;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ZaakRepositoryWithBagRelationshipsImpl implements ZaakRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ZAAKS_PARAMETER = "zaaks";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Zaak> fetchBagRelationships(Optional<Zaak> zaak) {
        return zaak.map(this::fetchKentDocuments).map(this::fetchAfhandelendmedewerkerMedewerkers);
    }

    @Override
    public Page<Zaak> fetchBagRelationships(Page<Zaak> zaaks) {
        return new PageImpl<>(fetchBagRelationships(zaaks.getContent()), zaaks.getPageable(), zaaks.getTotalElements());
    }

    @Override
    public List<Zaak> fetchBagRelationships(List<Zaak> zaaks) {
        return Optional.of(zaaks)
            .map(this::fetchKentDocuments)
            .map(this::fetchAfhandelendmedewerkerMedewerkers)
            .orElse(Collections.emptyList());
    }

    Zaak fetchKentDocuments(Zaak result) {
        return entityManager
            .createQuery("select zaak from Zaak zaak left join fetch zaak.kentDocuments where zaak.id = :id", Zaak.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Zaak> fetchKentDocuments(List<Zaak> zaaks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, zaaks.size()).forEach(index -> order.put(zaaks.get(index).getId(), index));
        List<Zaak> result = entityManager
            .createQuery("select zaak from Zaak zaak left join fetch zaak.kentDocuments where zaak in :zaaks", Zaak.class)
            .setParameter(ZAAKS_PARAMETER, zaaks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Zaak fetchAfhandelendmedewerkerMedewerkers(Zaak result) {
        return entityManager
            .createQuery("select zaak from Zaak zaak left join fetch zaak.afhandelendmedewerkerMedewerkers where zaak.id = :id", Zaak.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Zaak> fetchAfhandelendmedewerkerMedewerkers(List<Zaak> zaaks) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, zaaks.size()).forEach(index -> order.put(zaaks.get(index).getId(), index));
        List<Zaak> result = entityManager
            .createQuery(
                "select zaak from Zaak zaak left join fetch zaak.afhandelendmedewerkerMedewerkers where zaak in :zaaks",
                Zaak.class
            )
            .setParameter(ZAAKS_PARAMETER, zaaks)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
