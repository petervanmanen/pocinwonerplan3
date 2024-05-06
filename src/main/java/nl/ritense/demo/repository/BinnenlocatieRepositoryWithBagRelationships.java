package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Binnenlocatie;
import org.springframework.data.domain.Page;

public interface BinnenlocatieRepositoryWithBagRelationships {
    Optional<Binnenlocatie> fetchBagRelationships(Optional<Binnenlocatie> binnenlocatie);

    List<Binnenlocatie> fetchBagRelationships(List<Binnenlocatie> binnenlocaties);

    Page<Binnenlocatie> fetchBagRelationships(Page<Binnenlocatie> binnenlocaties);
}
