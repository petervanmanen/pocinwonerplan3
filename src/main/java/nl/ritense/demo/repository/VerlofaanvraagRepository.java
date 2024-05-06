package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verlofaanvraag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verlofaanvraag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerlofaanvraagRepository extends JpaRepository<Verlofaanvraag, Long> {}
