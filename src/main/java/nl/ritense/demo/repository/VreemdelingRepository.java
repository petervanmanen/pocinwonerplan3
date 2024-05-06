package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vreemdeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vreemdeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VreemdelingRepository extends JpaRepository<Vreemdeling, Long> {}
