package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bredeintake;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bredeintake entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BredeintakeRepository extends JpaRepository<Bredeintake, Long> {}
