package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Koppeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Koppeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KoppelingRepository extends JpaRepository<Koppeling, Long> {}
