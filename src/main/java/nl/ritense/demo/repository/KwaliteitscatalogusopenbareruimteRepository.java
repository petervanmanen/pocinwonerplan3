package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kwaliteitscatalogusopenbareruimte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kwaliteitscatalogusopenbareruimte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KwaliteitscatalogusopenbareruimteRepository extends JpaRepository<Kwaliteitscatalogusopenbareruimte, Long> {}
