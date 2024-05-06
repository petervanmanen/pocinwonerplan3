package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Activiteitsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Activiteitsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiviteitsoortRepository extends JpaRepository<Activiteitsoort, Long> {}
