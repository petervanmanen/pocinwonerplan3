package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Locatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Locatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocatieRepository extends JpaRepository<Locatie, Long> {}
