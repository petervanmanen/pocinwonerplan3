package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Klachtleerlingenvervoer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Klachtleerlingenvervoer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlachtleerlingenvervoerRepository extends JpaRepository<Klachtleerlingenvervoer, Long> {}
