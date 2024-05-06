package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Artefact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artefact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtefactRepository extends JpaRepository<Artefact, Long> {}
