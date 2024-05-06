package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Areaal;
import org.springframework.data.domain.Page;

public interface AreaalRepositoryWithBagRelationships {
    Optional<Areaal> fetchBagRelationships(Optional<Areaal> areaal);

    List<Areaal> fetchBagRelationships(List<Areaal> areaals);

    Page<Areaal> fetchBagRelationships(Page<Areaal> areaals);
}
