package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kast;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KastRepository extends JpaRepository<Kast, Long> {}
