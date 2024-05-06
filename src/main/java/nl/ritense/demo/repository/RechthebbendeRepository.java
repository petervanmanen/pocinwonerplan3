package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rechthebbende;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rechthebbende entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RechthebbendeRepository extends JpaRepository<Rechthebbende, Long> {}
