package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ingezetene;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ingezetene entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngezeteneRepository extends JpaRepository<Ingezetene, Long> {}
