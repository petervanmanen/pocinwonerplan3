package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verzuimsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verzuimsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerzuimsoortRepository extends JpaRepository<Verzuimsoort, Long> {}
