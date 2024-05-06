package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Fraudesoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fraudesoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraudesoortRepository extends JpaRepository<Fraudesoort, Long> {}
