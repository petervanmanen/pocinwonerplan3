package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beschikkingsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beschikkingsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeschikkingsoortRepository extends JpaRepository<Beschikkingsoort, Long> {}
