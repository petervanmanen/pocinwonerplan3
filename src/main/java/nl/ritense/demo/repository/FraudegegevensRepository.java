package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Fraudegegevens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fraudegegevens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraudegegevensRepository extends JpaRepository<Fraudegegevens, Long> {}
