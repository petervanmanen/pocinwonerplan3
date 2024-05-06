package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanvraagofmeldingRepository extends JpaRepository<Aanvraagofmelding, Long> {}
