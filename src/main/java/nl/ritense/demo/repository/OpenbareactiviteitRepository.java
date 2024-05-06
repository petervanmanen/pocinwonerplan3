package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Openbareactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Openbareactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpenbareactiviteitRepository extends JpaRepository<Openbareactiviteit, Long> {}
