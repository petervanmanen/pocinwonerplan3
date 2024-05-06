package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Nummeraanduiding;
import org.springframework.data.domain.Page;

public interface NummeraanduidingRepositoryWithBagRelationships {
    Optional<Nummeraanduiding> fetchBagRelationships(Optional<Nummeraanduiding> nummeraanduiding);

    List<Nummeraanduiding> fetchBagRelationships(List<Nummeraanduiding> nummeraanduidings);

    Page<Nummeraanduiding> fetchBagRelationships(Page<Nummeraanduiding> nummeraanduidings);
}
