package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Behandelsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Behandelsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BehandelsoortRepository extends JpaRepository<Behandelsoort, Long> {}
