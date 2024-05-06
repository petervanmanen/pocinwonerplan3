package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Regeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Regeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegelingRepository extends JpaRepository<Regeling, Long> {}
