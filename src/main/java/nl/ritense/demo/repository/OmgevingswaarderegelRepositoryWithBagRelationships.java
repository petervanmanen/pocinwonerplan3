package nl.ritense.demo.repository;

import java.util.List;
import java.util.Optional;
import nl.ritense.demo.domain.Omgevingswaarderegel;
import org.springframework.data.domain.Page;

public interface OmgevingswaarderegelRepositoryWithBagRelationships {
    Optional<Omgevingswaarderegel> fetchBagRelationships(Optional<Omgevingswaarderegel> omgevingswaarderegel);

    List<Omgevingswaarderegel> fetchBagRelationships(List<Omgevingswaarderegel> omgevingswaarderegels);

    Page<Omgevingswaarderegel> fetchBagRelationships(Page<Omgevingswaarderegel> omgevingswaarderegels);
}
