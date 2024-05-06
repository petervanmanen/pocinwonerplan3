package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Museumrelatie;
import org.springframework.data.domain.Page;

public interface MuseumrelatieRepositoryWithBagRelationships {
    Optional<Museumrelatie> fetchBagRelationships(Optional<Museumrelatie> museumrelatie);

    List<Museumrelatie> fetchBagRelationships(List<Museumrelatie> museumrelaties);

    Page<Museumrelatie> fetchBagRelationships(Page<Museumrelatie> museumrelaties);
}
