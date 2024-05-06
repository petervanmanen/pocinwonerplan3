package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Crowmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Crowmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrowmeldingRepository extends JpaRepository<Crowmelding, Long> {}
