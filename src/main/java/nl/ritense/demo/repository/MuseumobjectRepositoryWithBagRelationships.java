package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Museumobject;
import org.springframework.data.domain.Page;

public interface MuseumobjectRepositoryWithBagRelationships {
    Optional<Museumobject> fetchBagRelationships(Optional<Museumobject> museumobject);

    List<Museumobject> fetchBagRelationships(List<Museumobject> museumobjects);

    Page<Museumobject> fetchBagRelationships(Page<Museumobject> museumobjects);
}
