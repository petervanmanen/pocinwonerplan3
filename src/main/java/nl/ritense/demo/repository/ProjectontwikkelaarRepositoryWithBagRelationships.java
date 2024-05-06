package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Projectontwikkelaar;
import org.springframework.data.domain.Page;

public interface ProjectontwikkelaarRepositoryWithBagRelationships {
    Optional<Projectontwikkelaar> fetchBagRelationships(Optional<Projectontwikkelaar> projectontwikkelaar);

    List<Projectontwikkelaar> fetchBagRelationships(List<Projectontwikkelaar> projectontwikkelaars);

    Page<Projectontwikkelaar> fetchBagRelationships(Page<Projectontwikkelaar> projectontwikkelaars);
}
