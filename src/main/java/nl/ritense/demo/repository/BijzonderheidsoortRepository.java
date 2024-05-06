package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bijzonderheidsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bijzonderheidsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BijzonderheidsoortRepository extends JpaRepository<Bijzonderheidsoort, Long> {}
