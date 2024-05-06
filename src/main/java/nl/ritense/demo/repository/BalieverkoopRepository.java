package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Balieverkoop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Balieverkoop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalieverkoopRepository extends JpaRepository<Balieverkoop, Long> {}
