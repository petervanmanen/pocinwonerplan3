package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortscheiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortscheiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortscheidingRepository extends JpaRepository<Soortscheiding, Long> {}
