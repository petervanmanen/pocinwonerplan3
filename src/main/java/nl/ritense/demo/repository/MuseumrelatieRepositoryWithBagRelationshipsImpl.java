package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Museumrelatie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MuseumrelatieRepositoryWithBagRelationshipsImpl implements MuseumrelatieRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MUSEUMRELATIES_PARAMETER = "museumrelaties";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Museumrelatie> fetchBagRelationships(Optional<Museumrelatie> museumrelatie) {
        return museumrelatie.map(this::fetchValtbinnenDoelgroeps);
    }

    @Override
    public Page<Museumrelatie> fetchBagRelationships(Page<Museumrelatie> museumrelaties) {
        return new PageImpl<>(
            fetchBagRelationships(museumrelaties.getContent()),
            museumrelaties.getPageable(),
            museumrelaties.getTotalElements()
        );
    }

    @Override
    public List<Museumrelatie> fetchBagRelationships(List<Museumrelatie> museumrelaties) {
        return Optional.of(museumrelaties).map(this::fetchValtbinnenDoelgroeps).orElse(Collections.emptyList());
    }

    Museumrelatie fetchValtbinnenDoelgroeps(Museumrelatie result) {
        return entityManager
            .createQuery(
                "select museumrelatie from Museumrelatie museumrelatie left join fetch museumrelatie.valtbinnenDoelgroeps where museumrelatie.id = :id",
                Museumrelatie.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Museumrelatie> fetchValtbinnenDoelgroeps(List<Museumrelatie> museumrelaties) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, museumrelaties.size()).forEach(index -> order.put(museumrelaties.get(index).getId(), index));
        List<Museumrelatie> result = entityManager
            .createQuery(
                "select museumrelatie from Museumrelatie museumrelatie left join fetch museumrelatie.valtbinnenDoelgroeps where museumrelatie in :museumrelaties",
                Museumrelatie.class
            )
            .setParameter(MUSEUMRELATIES_PARAMETER, museumrelaties)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
