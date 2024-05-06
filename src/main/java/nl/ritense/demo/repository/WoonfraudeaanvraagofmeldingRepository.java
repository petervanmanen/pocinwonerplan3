package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Woonfraudeaanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Woonfraudeaanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WoonfraudeaanvraagofmeldingRepository extends JpaRepository<Woonfraudeaanvraagofmelding, Long> {}
