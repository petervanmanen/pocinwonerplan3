package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ouderofverzorger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ouderofverzorger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OuderofverzorgerRepository extends JpaRepository<Ouderofverzorger, Long> {}
