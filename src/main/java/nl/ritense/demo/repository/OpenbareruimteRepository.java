package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Openbareruimte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Openbareruimte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpenbareruimteRepository extends JpaRepository<Openbareruimte, Long> {}
