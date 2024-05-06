package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Scheiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Scheiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheidingRepository extends JpaRepository<Scheiding, Long> {}
