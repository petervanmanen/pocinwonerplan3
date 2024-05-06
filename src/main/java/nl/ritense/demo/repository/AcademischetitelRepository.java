package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Academischetitel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Academischetitel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademischetitelRepository extends JpaRepository<Academischetitel, Long> {}
