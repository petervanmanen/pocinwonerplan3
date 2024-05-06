package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Attribuutsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Attribuutsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttribuutsoortRepository extends JpaRepository<Attribuutsoort, String> {}
