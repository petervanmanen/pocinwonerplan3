package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanvraagleerlingenvervoer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraagleerlingenvervoer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanvraagleerlingenvervoerRepository extends JpaRepository<Aanvraagleerlingenvervoer, Long> {}
