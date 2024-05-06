package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Schouwronde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Schouwronde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchouwrondeRepository extends JpaRepository<Schouwronde, Long> {}
