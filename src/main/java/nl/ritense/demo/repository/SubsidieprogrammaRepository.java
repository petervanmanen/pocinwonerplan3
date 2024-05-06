package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subsidieprogramma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subsidieprogramma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubsidieprogrammaRepository extends JpaRepository<Subsidieprogramma, Long> {}
