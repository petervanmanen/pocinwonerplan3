package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SchoolRepositoryWithBagRelationshipsImpl implements SchoolRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SCHOOLS_PARAMETER = "schools";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<School> fetchBagRelationships(Optional<School> school) {
        return school.map(this::fetchKentOnderwijsloopbaans).map(this::fetchHeeftOnderwijssoorts).map(this::fetchGebruiktSportlocaties);
    }

    @Override
    public Page<School> fetchBagRelationships(Page<School> schools) {
        return new PageImpl<>(fetchBagRelationships(schools.getContent()), schools.getPageable(), schools.getTotalElements());
    }

    @Override
    public List<School> fetchBagRelationships(List<School> schools) {
        return Optional.of(schools)
            .map(this::fetchKentOnderwijsloopbaans)
            .map(this::fetchHeeftOnderwijssoorts)
            .map(this::fetchGebruiktSportlocaties)
            .orElse(Collections.emptyList());
    }

    School fetchKentOnderwijsloopbaans(School result) {
        return entityManager
            .createQuery(
                "select school from School school left join fetch school.kentOnderwijsloopbaans where school.id = :id",
                School.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<School> fetchKentOnderwijsloopbaans(List<School> schools) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, schools.size()).forEach(index -> order.put(schools.get(index).getId(), index));
        List<School> result = entityManager
            .createQuery(
                "select school from School school left join fetch school.kentOnderwijsloopbaans where school in :schools",
                School.class
            )
            .setParameter(SCHOOLS_PARAMETER, schools)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    School fetchHeeftOnderwijssoorts(School result) {
        return entityManager
            .createQuery("select school from School school left join fetch school.heeftOnderwijssoorts where school.id = :id", School.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<School> fetchHeeftOnderwijssoorts(List<School> schools) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, schools.size()).forEach(index -> order.put(schools.get(index).getId(), index));
        List<School> result = entityManager
            .createQuery(
                "select school from School school left join fetch school.heeftOnderwijssoorts where school in :schools",
                School.class
            )
            .setParameter(SCHOOLS_PARAMETER, schools)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    School fetchGebruiktSportlocaties(School result) {
        return entityManager
            .createQuery(
                "select school from School school left join fetch school.gebruiktSportlocaties where school.id = :id",
                School.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<School> fetchGebruiktSportlocaties(List<School> schools) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, schools.size()).forEach(index -> order.put(schools.get(index).getId(), index));
        List<School> result = entityManager
            .createQuery(
                "select school from School school left join fetch school.gebruiktSportlocaties where school in :schools",
                School.class
            )
            .setParameter(SCHOOLS_PARAMETER, schools)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
