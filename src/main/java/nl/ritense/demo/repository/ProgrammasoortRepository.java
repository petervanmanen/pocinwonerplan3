package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Programmasoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Programmasoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgrammasoortRepository extends JpaRepository<Programmasoort, Long> {}
