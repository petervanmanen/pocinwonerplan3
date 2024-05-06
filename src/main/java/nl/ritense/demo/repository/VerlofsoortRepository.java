package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verlofsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verlofsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerlofsoortRepository extends JpaRepository<Verlofsoort, Long> {}
