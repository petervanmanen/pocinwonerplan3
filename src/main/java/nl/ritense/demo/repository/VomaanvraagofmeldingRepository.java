package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vomaanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vomaanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VomaanvraagofmeldingRepository extends JpaRepository<Vomaanvraagofmelding, Long> {}
