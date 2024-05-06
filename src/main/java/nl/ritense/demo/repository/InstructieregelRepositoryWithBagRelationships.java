package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Instructieregel;
import org.springframework.data.domain.Page;

public interface InstructieregelRepositoryWithBagRelationships {
    Optional<Instructieregel> fetchBagRelationships(Optional<Instructieregel> instructieregel);

    List<Instructieregel> fetchBagRelationships(List<Instructieregel> instructieregels);

    Page<Instructieregel> fetchBagRelationships(Page<Instructieregel> instructieregels);
}
