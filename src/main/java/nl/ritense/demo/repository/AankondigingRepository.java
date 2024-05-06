package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aankondiging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aankondiging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AankondigingRepository extends JpaRepository<Aankondiging, Long> {}
