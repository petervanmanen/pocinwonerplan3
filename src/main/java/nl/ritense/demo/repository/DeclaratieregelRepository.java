package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Declaratieregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Declaratieregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeclaratieregelRepository extends JpaRepository<Declaratieregel, Long> {}
