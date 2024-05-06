package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitnodiging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitnodiging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitnodigingRepository extends JpaRepository<Uitnodiging, Long> {}
