package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sociaalteamdossiersoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sociaalteamdossiersoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SociaalteamdossiersoortRepository extends JpaRepository<Sociaalteamdossiersoort, Long> {}
