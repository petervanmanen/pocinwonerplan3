package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sollicitant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sollicitant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SollicitantRepository extends JpaRepository<Sollicitant, Long> {}
