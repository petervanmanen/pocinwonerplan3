package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanbesteding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanbesteding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanbestedingRepository extends JpaRepository<Aanbesteding, Long> {}
