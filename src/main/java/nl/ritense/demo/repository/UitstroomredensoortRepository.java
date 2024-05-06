package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitstroomredensoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitstroomredensoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitstroomredensoortRepository extends JpaRepository<Uitstroomredensoort, Long> {}
