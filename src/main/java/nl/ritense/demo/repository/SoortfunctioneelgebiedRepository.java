package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortfunctioneelgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortfunctioneelgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortfunctioneelgebiedRepository extends JpaRepository<Soortfunctioneelgebied, Long> {}
