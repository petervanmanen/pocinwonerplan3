package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Projectontwikkelaar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProjectontwikkelaarRepositoryWithBagRelationshipsImpl implements ProjectontwikkelaarRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PROJECTONTWIKKELAARS_PARAMETER = "projectontwikkelaars";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Projectontwikkelaar> fetchBagRelationships(Optional<Projectontwikkelaar> projectontwikkelaar) {
        return projectontwikkelaar.map(this::fetchHeeftPlans);
    }

    @Override
    public Page<Projectontwikkelaar> fetchBagRelationships(Page<Projectontwikkelaar> projectontwikkelaars) {
        return new PageImpl<>(
            fetchBagRelationships(projectontwikkelaars.getContent()),
            projectontwikkelaars.getPageable(),
            projectontwikkelaars.getTotalElements()
        );
    }

    @Override
    public List<Projectontwikkelaar> fetchBagRelationships(List<Projectontwikkelaar> projectontwikkelaars) {
        return Optional.of(projectontwikkelaars).map(this::fetchHeeftPlans).orElse(Collections.emptyList());
    }

    Projectontwikkelaar fetchHeeftPlans(Projectontwikkelaar result) {
        return entityManager
            .createQuery(
                "select projectontwikkelaar from Projectontwikkelaar projectontwikkelaar left join fetch projectontwikkelaar.heeftPlans where projectontwikkelaar.id = :id",
                Projectontwikkelaar.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Projectontwikkelaar> fetchHeeftPlans(List<Projectontwikkelaar> projectontwikkelaars) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, projectontwikkelaars.size()).forEach(index -> order.put(projectontwikkelaars.get(index).getId(), index));
        List<Projectontwikkelaar> result = entityManager
            .createQuery(
                "select projectontwikkelaar from Projectontwikkelaar projectontwikkelaar left join fetch projectontwikkelaar.heeftPlans where projectontwikkelaar in :projectontwikkelaars",
                Projectontwikkelaar.class
            )
            .setParameter(PROJECTONTWIKKELAARS_PARAMETER, projectontwikkelaars)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
