package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanwezigedeelnemer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanwezigedeelnemer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanwezigedeelnemerRepository extends JpaRepository<Aanwezigedeelnemer, Long> {}
