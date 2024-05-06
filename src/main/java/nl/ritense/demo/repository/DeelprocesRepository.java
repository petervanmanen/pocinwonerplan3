package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Deelproces;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Deelproces entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeelprocesRepository extends JpaRepository<Deelproces, Long> {}
