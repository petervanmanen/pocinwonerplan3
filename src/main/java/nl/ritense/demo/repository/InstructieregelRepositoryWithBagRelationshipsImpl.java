package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Instructieregel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class InstructieregelRepositoryWithBagRelationshipsImpl implements InstructieregelRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String INSTRUCTIEREGELS_PARAMETER = "instructieregels";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Instructieregel> fetchBagRelationships(Optional<Instructieregel> instructieregel) {
        return instructieregel.map(this::fetchBeschrijftgebiedsaanwijzingGebiedsaanwijzings);
    }

    @Override
    public Page<Instructieregel> fetchBagRelationships(Page<Instructieregel> instructieregels) {
        return new PageImpl<>(
            fetchBagRelationships(instructieregels.getContent()),
            instructieregels.getPageable(),
            instructieregels.getTotalElements()
        );
    }

    @Override
    public List<Instructieregel> fetchBagRelationships(List<Instructieregel> instructieregels) {
        return Optional.of(instructieregels).map(this::fetchBeschrijftgebiedsaanwijzingGebiedsaanwijzings).orElse(Collections.emptyList());
    }

    Instructieregel fetchBeschrijftgebiedsaanwijzingGebiedsaanwijzings(Instructieregel result) {
        return entityManager
            .createQuery(
                "select instructieregel from Instructieregel instructieregel left join fetch instructieregel.beschrijftgebiedsaanwijzingGebiedsaanwijzings where instructieregel.id = :id",
                Instructieregel.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Instructieregel> fetchBeschrijftgebiedsaanwijzingGebiedsaanwijzings(List<Instructieregel> instructieregels) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, instructieregels.size()).forEach(index -> order.put(instructieregels.get(index).getId(), index));
        List<Instructieregel> result = entityManager
            .createQuery(
                "select instructieregel from Instructieregel instructieregel left join fetch instructieregel.beschrijftgebiedsaanwijzingGebiedsaanwijzings where instructieregel in :instructieregels",
                Instructieregel.class
            )
            .setParameter(INSTRUCTIEREGELS_PARAMETER, instructieregels)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
