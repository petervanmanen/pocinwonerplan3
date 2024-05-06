package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beschikkingleerlingenvervoer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beschikkingleerlingenvervoer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeschikkingleerlingenvervoerRepository extends JpaRepository<Beschikkingleerlingenvervoer, Long> {}
