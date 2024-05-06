package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Indeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Indeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndelingRepository extends JpaRepository<Indeling, Long> {}
