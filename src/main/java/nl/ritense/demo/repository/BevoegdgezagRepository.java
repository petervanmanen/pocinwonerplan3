package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bevoegdgezag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bevoegdgezag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BevoegdgezagRepository extends JpaRepository<Bevoegdgezag, Long> {}
