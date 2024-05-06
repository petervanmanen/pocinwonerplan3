package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortkunstwerk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortkunstwerk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortkunstwerkRepository extends JpaRepository<Soortkunstwerk, Long> {}
