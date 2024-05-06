package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.School;
import org.springframework.data.domain.Page;

public interface SchoolRepositoryWithBagRelationships {
    Optional<School> fetchBagRelationships(Optional<School> school);

    List<School> fetchBagRelationships(List<School> schools);

    Page<School> fetchBagRelationships(Page<School> schools);
}
