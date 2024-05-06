package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Historischerol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Historischerol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistorischerolRepository extends JpaRepository<Historischerol, Long> {}
