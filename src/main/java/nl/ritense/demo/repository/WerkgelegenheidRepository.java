package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Werkgelegenheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Werkgelegenheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WerkgelegenheidRepository extends JpaRepository<Werkgelegenheid, Long> {}
