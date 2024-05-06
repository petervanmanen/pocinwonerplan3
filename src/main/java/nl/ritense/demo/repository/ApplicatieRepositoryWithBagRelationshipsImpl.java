package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Applicatie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ApplicatieRepositoryWithBagRelationshipsImpl implements ApplicatieRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String APPLICATIES_PARAMETER = "applicaties";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Applicatie> fetchBagRelationships(Optional<Applicatie> applicatie) {
        return applicatie.map(this::fetchHeeftdocumentenDocuments).map(this::fetchRollenMedewerkers);
    }

    @Override
    public Page<Applicatie> fetchBagRelationships(Page<Applicatie> applicaties) {
        return new PageImpl<>(fetchBagRelationships(applicaties.getContent()), applicaties.getPageable(), applicaties.getTotalElements());
    }

    @Override
    public List<Applicatie> fetchBagRelationships(List<Applicatie> applicaties) {
        return Optional.of(applicaties)
            .map(this::fetchHeeftdocumentenDocuments)
            .map(this::fetchRollenMedewerkers)
            .orElse(Collections.emptyList());
    }

    Applicatie fetchHeeftdocumentenDocuments(Applicatie result) {
        return entityManager
            .createQuery(
                "select applicatie from Applicatie applicatie left join fetch applicatie.heeftdocumentenDocuments where applicatie.id = :id",
                Applicatie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Applicatie> fetchHeeftdocumentenDocuments(List<Applicatie> applicaties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, applicaties.size()).forEach(index -> order.put(applicaties.get(index).getId(), index));
        List<Applicatie> result = entityManager
            .createQuery(
                "select applicatie from Applicatie applicatie left join fetch applicatie.heeftdocumentenDocuments where applicatie in :applicaties",
                Applicatie.class
            )
            .setParameter(APPLICATIES_PARAMETER, applicaties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Applicatie fetchRollenMedewerkers(Applicatie result) {
        return entityManager
            .createQuery(
                "select applicatie from Applicatie applicatie left join fetch applicatie.rollenMedewerkers where applicatie.id = :id",
                Applicatie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Applicatie> fetchRollenMedewerkers(List<Applicatie> applicaties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, applicaties.size()).forEach(index -> order.put(applicaties.get(index).getId(), index));
        List<Applicatie> result = entityManager
            .createQuery(
                "select applicatie from Applicatie applicatie left join fetch applicatie.rollenMedewerkers where applicatie in :applicaties",
                Applicatie.class
            )
            .setParameter(APPLICATIES_PARAMETER, applicaties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
