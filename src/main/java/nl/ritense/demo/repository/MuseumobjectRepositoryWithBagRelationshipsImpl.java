package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Museumobject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MuseumobjectRepositoryWithBagRelationshipsImpl implements MuseumobjectRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MUSEUMOBJECTS_PARAMETER = "museumobjects";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Museumobject> fetchBagRelationships(Optional<Museumobject> museumobject) {
        return museumobject.map(this::fetchHeeftBelanghebbendes).map(this::fetchOnderdeelTentoonstellings);
    }

    @Override
    public Page<Museumobject> fetchBagRelationships(Page<Museumobject> museumobjects) {
        return new PageImpl<>(
            fetchBagRelationships(museumobjects.getContent()),
            museumobjects.getPageable(),
            museumobjects.getTotalElements()
        );
    }

    @Override
    public List<Museumobject> fetchBagRelationships(List<Museumobject> museumobjects) {
        return Optional.of(museumobjects)
            .map(this::fetchHeeftBelanghebbendes)
            .map(this::fetchOnderdeelTentoonstellings)
            .orElse(Collections.emptyList());
    }

    Museumobject fetchHeeftBelanghebbendes(Museumobject result) {
        return entityManager
            .createQuery(
                "select museumobject from Museumobject museumobject left join fetch museumobject.heeftBelanghebbendes where museumobject.id = :id",
                Museumobject.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Museumobject> fetchHeeftBelanghebbendes(List<Museumobject> museumobjects) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, museumobjects.size()).forEach(index -> order.put(museumobjects.get(index).getId(), index));
        List<Museumobject> result = entityManager
            .createQuery(
                "select museumobject from Museumobject museumobject left join fetch museumobject.heeftBelanghebbendes where museumobject in :museumobjects",
                Museumobject.class
            )
            .setParameter(MUSEUMOBJECTS_PARAMETER, museumobjects)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Museumobject fetchOnderdeelTentoonstellings(Museumobject result) {
        return entityManager
            .createQuery(
                "select museumobject from Museumobject museumobject left join fetch museumobject.onderdeelTentoonstellings where museumobject.id = :id",
                Museumobject.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Museumobject> fetchOnderdeelTentoonstellings(List<Museumobject> museumobjects) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, museumobjects.size()).forEach(index -> order.put(museumobjects.get(index).getId(), index));
        List<Museumobject> result = entityManager
            .createQuery(
                "select museumobject from Museumobject museumobject left join fetch museumobject.onderdeelTentoonstellings where museumobject in :museumobjects",
                Museumobject.class
            )
            .setParameter(MUSEUMOBJECTS_PARAMETER, museumobjects)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
