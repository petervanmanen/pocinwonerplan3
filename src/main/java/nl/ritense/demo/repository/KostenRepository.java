package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kosten;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kosten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KostenRepository extends JpaRepository<Kosten, Long> {}
