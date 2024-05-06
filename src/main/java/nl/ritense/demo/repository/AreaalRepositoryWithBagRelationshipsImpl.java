package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Areaal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AreaalRepositoryWithBagRelationshipsImpl implements AreaalRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String AREAALS_PARAMETER = "areaals";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Areaal> fetchBagRelationships(Optional<Areaal> areaal) {
        return areaal.map(this::fetchLigtinBuurts).map(this::fetchValtbinnenWijks).map(this::fetchBinnenSchouwrondes);
    }

    @Override
    public Page<Areaal> fetchBagRelationships(Page<Areaal> areaals) {
        return new PageImpl<>(fetchBagRelationships(areaals.getContent()), areaals.getPageable(), areaals.getTotalElements());
    }

    @Override
    public List<Areaal> fetchBagRelationships(List<Areaal> areaals) {
        return Optional.of(areaals)
            .map(this::fetchLigtinBuurts)
            .map(this::fetchValtbinnenWijks)
            .map(this::fetchBinnenSchouwrondes)
            .orElse(Collections.emptyList());
    }

    Areaal fetchLigtinBuurts(Areaal result) {
        return entityManager
            .createQuery("select areaal from Areaal areaal left join fetch areaal.ligtinBuurts where areaal.id = :id", Areaal.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Areaal> fetchLigtinBuurts(List<Areaal> areaals) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, areaals.size()).forEach(index -> order.put(areaals.get(index).getId(), index));
        List<Areaal> result = entityManager
            .createQuery("select areaal from Areaal areaal left join fetch areaal.ligtinBuurts where areaal in :areaals", Areaal.class)
            .setParameter(AREAALS_PARAMETER, areaals)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Areaal fetchValtbinnenWijks(Areaal result) {
        return entityManager
            .createQuery("select areaal from Areaal areaal left join fetch areaal.valtbinnenWijks where areaal.id = :id", Areaal.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Areaal> fetchValtbinnenWijks(List<Areaal> areaals) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, areaals.size()).forEach(index -> order.put(areaals.get(index).getId(), index));
        List<Areaal> result = entityManager
            .createQuery("select areaal from Areaal areaal left join fetch areaal.valtbinnenWijks where areaal in :areaals", Areaal.class)
            .setParameter(AREAALS_PARAMETER, areaals)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Areaal fetchBinnenSchouwrondes(Areaal result) {
        return entityManager
            .createQuery("select areaal from Areaal areaal left join fetch areaal.binnenSchouwrondes where areaal.id = :id", Areaal.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Areaal> fetchBinnenSchouwrondes(List<Areaal> areaals) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, areaals.size()).forEach(index -> order.put(areaals.get(index).getId(), index));
        List<Areaal> result = entityManager
            .createQuery(
                "select areaal from Areaal areaal left join fetch areaal.binnenSchouwrondes where areaal in :areaals",
                Areaal.class
            )
            .setParameter(AREAALS_PARAMETER, areaals)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
