package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Standplaats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Standplaats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StandplaatsRepository extends JpaRepository<Standplaats, Long> {}
