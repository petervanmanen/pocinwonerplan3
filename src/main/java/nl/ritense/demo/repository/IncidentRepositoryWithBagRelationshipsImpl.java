package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class IncidentRepositoryWithBagRelationshipsImpl implements IncidentRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String INCIDENTS_PARAMETER = "incidents";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Incident> fetchBagRelationships(Optional<Incident> incident) {
        return incident.map(this::fetchBetreftMuseumobjects);
    }

    @Override
    public Page<Incident> fetchBagRelationships(Page<Incident> incidents) {
        return new PageImpl<>(fetchBagRelationships(incidents.getContent()), incidents.getPageable(), incidents.getTotalElements());
    }

    @Override
    public List<Incident> fetchBagRelationships(List<Incident> incidents) {
        return Optional.of(incidents).map(this::fetchBetreftMuseumobjects).orElse(Collections.emptyList());
    }

    Incident fetchBetreftMuseumobjects(Incident result) {
        return entityManager
            .createQuery(
                "select incident from Incident incident left join fetch incident.betreftMuseumobjects where incident.id = :id",
                Incident.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Incident> fetchBetreftMuseumobjects(List<Incident> incidents) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, incidents.size()).forEach(index -> order.put(incidents.get(index).getId(), index));
        List<Incident> result = entityManager
            .createQuery(
                "select incident from Incident incident left join fetch incident.betreftMuseumobjects where incident in :incidents",
                Incident.class
            )
            .setParameter(INCIDENTS_PARAMETER, incidents)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
