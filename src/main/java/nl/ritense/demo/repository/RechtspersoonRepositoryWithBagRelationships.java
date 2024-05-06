package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Rechtspersoon;
import org.springframework.data.domain.Page;

public interface RechtspersoonRepositoryWithBagRelationships {
    Optional<Rechtspersoon> fetchBagRelationships(Optional<Rechtspersoon> rechtspersoon);

    List<Rechtspersoon> fetchBagRelationships(List<Rechtspersoon> rechtspersoons);

    Page<Rechtspersoon> fetchBagRelationships(Page<Rechtspersoon> rechtspersoons);
}
