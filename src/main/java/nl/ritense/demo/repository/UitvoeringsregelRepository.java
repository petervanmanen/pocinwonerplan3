package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitvoeringsregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitvoeringsregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitvoeringsregelRepository extends JpaRepository<Uitvoeringsregel, Long> {}
