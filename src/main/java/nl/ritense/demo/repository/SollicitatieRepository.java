package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sollicitatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sollicitatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SollicitatieRepository extends JpaRepository<Sollicitatie, Long> {}
