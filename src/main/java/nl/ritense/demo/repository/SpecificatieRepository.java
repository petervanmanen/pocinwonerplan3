package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Specificatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Specificatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecificatieRepository extends JpaRepository<Specificatie, Long> {}
