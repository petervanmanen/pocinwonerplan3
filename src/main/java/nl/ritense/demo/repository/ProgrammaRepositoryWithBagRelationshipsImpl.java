package nl.ritense.demo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import nl.ritense.demo.domain.Programma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProgrammaRepositoryWithBagRelationshipsImpl implements ProgrammaRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PROGRAMMAS_PARAMETER = "programmas";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Programma> fetchBagRelationships(Optional<Programma> programma) {
        return programma.map(this::fetchVoorProgrammasoorts);
    }

    @Override
    public Page<Programma> fetchBagRelationships(Page<Programma> programmas) {
        return new PageImpl<>(fetchBagRelationships(programmas.getContent()), programmas.getPageable(), programmas.getTotalElements());
    }

    @Override
    public List<Programma> fetchBagRelationships(List<Programma> programmas) {
        return Optional.of(programmas).map(this::fetchVoorProgrammasoorts).orElse(Collections.emptyList());
    }

    Programma fetchVoorProgrammasoorts(Programma result) {
        return entityManager
            .createQuery(
                "select programma from Programma programma left join fetch programma.voorProgrammasoorts where programma.id = :id",
                Programma.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Programma> fetchVoorProgrammasoorts(List<Programma> programmas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, programmas.size()).forEach(index -> order.put(programmas.get(index).getId(), index));
        List<Programma> result = entityManager
            .createQuery(
                "select programma from Programma programma left join fetch programma.voorProgrammasoorts where programma in :programmas",
                Programma.class
            )
            .setParameter(PROGRAMMAS_PARAMETER, programmas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
