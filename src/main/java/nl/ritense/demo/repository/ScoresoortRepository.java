package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Scoresoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Scoresoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScoresoortRepository extends JpaRepository<Scoresoort, Long> {}
