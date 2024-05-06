package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Subsidieaanvraag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Subsidieaanvraag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubsidieaanvraagRepository extends JpaRepository<Subsidieaanvraag, Long> {}
