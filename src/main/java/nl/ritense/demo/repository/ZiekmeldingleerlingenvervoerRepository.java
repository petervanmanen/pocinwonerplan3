package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ziekmeldingleerlingenvervoer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ziekmeldingleerlingenvervoer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZiekmeldingleerlingenvervoerRepository extends JpaRepository<Ziekmeldingleerlingenvervoer, Long> {}
