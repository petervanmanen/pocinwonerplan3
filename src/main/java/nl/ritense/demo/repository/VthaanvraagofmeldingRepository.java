package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vthaanvraagofmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vthaanvraagofmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VthaanvraagofmeldingRepository extends JpaRepository<Vthaanvraagofmelding, Long> {}
