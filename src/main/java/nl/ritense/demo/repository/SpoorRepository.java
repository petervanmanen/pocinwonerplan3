package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Spoor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Spoor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpoorRepository extends JpaRepository<Spoor, Long> {}
