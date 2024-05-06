package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Loopbaanstap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Loopbaanstap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoopbaanstapRepository extends JpaRepository<Loopbaanstap, Long> {}
