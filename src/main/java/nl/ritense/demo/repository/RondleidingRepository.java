package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rondleiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rondleiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RondleidingRepository extends JpaRepository<Rondleiding, Long> {}
