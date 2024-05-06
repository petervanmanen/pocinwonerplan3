package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Regelingsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Regelingsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegelingsoortRepository extends JpaRepository<Regelingsoort, Long> {}
