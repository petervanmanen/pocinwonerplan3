package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Verblijfsobject;
import org.springframework.data.domain.Page;

public interface VerblijfsobjectRepositoryWithBagRelationships {
    Optional<Verblijfsobject> fetchBagRelationships(Optional<Verblijfsobject> verblijfsobject);

    List<Verblijfsobject> fetchBagRelationships(List<Verblijfsobject> verblijfsobjects);

    Page<Verblijfsobject> fetchBagRelationships(Page<Verblijfsobject> verblijfsobjects);
}
