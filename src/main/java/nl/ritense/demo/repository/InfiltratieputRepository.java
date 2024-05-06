package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Infiltratieput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Infiltratieput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfiltratieputRepository extends JpaRepository<Infiltratieput, Long> {}
