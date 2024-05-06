package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beoordeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beoordeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeoordelingRepository extends JpaRepository<Beoordeling, Long> {}
