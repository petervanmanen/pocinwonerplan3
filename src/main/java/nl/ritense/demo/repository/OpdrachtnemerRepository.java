package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Opdrachtnemer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Opdrachtnemer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpdrachtnemerRepository extends JpaRepository<Opdrachtnemer, Long> {}
