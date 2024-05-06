package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Redenverliesnationaliteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Redenverliesnationaliteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RedenverliesnationaliteitRepository extends JpaRepository<Redenverliesnationaliteit, Long> {}
