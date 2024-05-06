package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Programma;
import org.springframework.data.domain.Page;

public interface ProgrammaRepositoryWithBagRelationships {
    Optional<Programma> fetchBagRelationships(Optional<Programma> programma);

    List<Programma> fetchBagRelationships(List<Programma> programmas);

    Page<Programma> fetchBagRelationships(Page<Programma> programmas);
}
