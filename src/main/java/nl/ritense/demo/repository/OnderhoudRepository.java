package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Onderhoud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Onderhoud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnderhoudRepository extends JpaRepository<Onderhoud, Long> {}
