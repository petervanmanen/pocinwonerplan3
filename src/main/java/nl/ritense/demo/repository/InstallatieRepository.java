package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Installatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Installatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstallatieRepository extends JpaRepository<Installatie, Long> {}
