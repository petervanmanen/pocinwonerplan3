package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitstroomreden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitstroomreden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitstroomredenRepository extends JpaRepository<Uitstroomreden, Long> {}
