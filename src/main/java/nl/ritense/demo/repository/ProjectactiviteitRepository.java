package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Projectactiviteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projectactiviteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectactiviteitRepository extends JpaRepository<Projectactiviteit, Long> {}
