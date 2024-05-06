package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Woonoverlastaanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Woonoverlastaanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WoonoverlastaanvraagofmeldingRepository extends JpaRepository<Woonoverlastaanvraagofmelding, Long> {}
