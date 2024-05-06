package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Drainageput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Drainageput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrainageputRepository extends JpaRepository<Drainageput, Long> {}
