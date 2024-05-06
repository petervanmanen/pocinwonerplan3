package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Voorzieningsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voorzieningsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoorzieningsoortRepository extends JpaRepository<Voorzieningsoort, Long> {}
