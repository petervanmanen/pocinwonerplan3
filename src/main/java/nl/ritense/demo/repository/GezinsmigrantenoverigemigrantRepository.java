package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gezinsmigrantenoverigemigrant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gezinsmigrantenoverigemigrant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GezinsmigrantenoverigemigrantRepository extends JpaRepository<Gezinsmigrantenoverigemigrant, Long> {}
