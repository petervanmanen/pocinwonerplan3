package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Versie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Versie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersieRepository extends JpaRepository<Versie, Long> {}
