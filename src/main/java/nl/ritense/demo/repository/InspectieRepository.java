package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inspectie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inspectie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspectieRepository extends JpaRepository<Inspectie, Long> {}
