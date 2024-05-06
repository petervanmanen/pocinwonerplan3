package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Betaling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Betaling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BetalingRepository extends JpaRepository<Betaling, Long> {}
