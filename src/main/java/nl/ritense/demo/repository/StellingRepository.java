package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Stelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StellingRepository extends JpaRepository<Stelling, Long> {}
