package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Waboaanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Waboaanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaboaanvraagofmeldingRepository extends JpaRepository<Waboaanvraagofmelding, Long> {}
