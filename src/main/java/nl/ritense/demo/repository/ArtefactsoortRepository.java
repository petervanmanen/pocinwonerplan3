package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Artefactsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artefactsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtefactsoortRepository extends JpaRepository<Artefactsoort, Long> {}
