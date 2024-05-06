package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sociaalteamdossier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sociaalteamdossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SociaalteamdossierRepository extends JpaRepository<Sociaalteamdossier, Long> {}
