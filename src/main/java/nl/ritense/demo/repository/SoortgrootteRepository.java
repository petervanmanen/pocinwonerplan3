package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortgrootte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortgrootte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortgrootteRepository extends JpaRepository<Soortgrootte, Long> {}
