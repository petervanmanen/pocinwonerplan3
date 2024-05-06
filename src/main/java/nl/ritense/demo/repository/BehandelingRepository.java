package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Behandeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Behandeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BehandelingRepository extends JpaRepository<Behandeling, Long> {}
