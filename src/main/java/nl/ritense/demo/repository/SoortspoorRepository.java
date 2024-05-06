package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortspoor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortspoor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortspoorRepository extends JpaRepository<Soortspoor, Long> {}
