package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Moraanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Moraanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoraanvraagofmeldingRepository extends JpaRepository<Moraanvraagofmelding, Long> {}
