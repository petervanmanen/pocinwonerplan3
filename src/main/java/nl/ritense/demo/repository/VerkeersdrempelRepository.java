package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verkeersdrempel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verkeersdrempel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerkeersdrempelRepository extends JpaRepository<Verkeersdrempel, Long> {}
