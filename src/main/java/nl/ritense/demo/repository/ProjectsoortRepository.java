package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Projectsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projectsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectsoortRepository extends JpaRepository<Projectsoort, Long> {}
