package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Gebiedsaanwijzing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class GebiedsaanwijzingRepositoryWithBagRelationshipsImpl implements GebiedsaanwijzingRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String GEBIEDSAANWIJZINGS_PARAMETER = "gebiedsaanwijzings";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Gebiedsaanwijzing> fetchBagRelationships(Optional<Gebiedsaanwijzing> gebiedsaanwijzing) {
        return gebiedsaanwijzing.map(this::fetchVerwijstnaarLocaties);
    }

    @Override
    public Page<Gebiedsaanwijzing> fetchBagRelationships(Page<Gebiedsaanwijzing> gebiedsaanwijzings) {
        return new PageImpl<>(
            fetchBagRelationships(gebiedsaanwijzings.getContent()),
            gebiedsaanwijzings.getPageable(),
            gebiedsaanwijzings.getTotalElements()
        );
    }

    @Override
    public List<Gebiedsaanwijzing> fetchBagRelationships(List<Gebiedsaanwijzing> gebiedsaanwijzings) {
        return Optional.of(gebiedsaanwijzings).map(this::fetchVerwijstnaarLocaties).orElse(Collections.emptyList());
    }

    Gebiedsaanwijzing fetchVerwijstnaarLocaties(Gebiedsaanwijzing result) {
        return entityManager
            .createQuery(
                "select gebiedsaanwijzing from Gebiedsaanwijzing gebiedsaanwijzing left join fetch gebiedsaanwijzing.verwijstnaarLocaties where gebiedsaanwijzing.id = :id",
                Gebiedsaanwijzing.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Gebiedsaanwijzing> fetchVerwijstnaarLocaties(List<Gebiedsaanwijzing> gebiedsaanwijzings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, gebiedsaanwijzings.size()).forEach(index -> order.put(gebiedsaanwijzings.get(index).getId(), index));
        List<Gebiedsaanwijzing> result = entityManager
            .createQuery(
                "select gebiedsaanwijzing from Gebiedsaanwijzing gebiedsaanwijzing left join fetch gebiedsaanwijzing.verwijstnaarLocaties where gebiedsaanwijzing in :gebiedsaanwijzings",
                Gebiedsaanwijzing.class
            )
            .setParameter(GEBIEDSAANWIJZINGS_PARAMETER, gebiedsaanwijzings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
