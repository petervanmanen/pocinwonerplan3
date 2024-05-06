package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Levering;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Levering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeveringRepository extends JpaRepository<Levering, Long> {}
