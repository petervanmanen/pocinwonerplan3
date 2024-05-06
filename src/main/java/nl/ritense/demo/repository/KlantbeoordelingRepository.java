package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Klantbeoordeling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Klantbeoordeling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KlantbeoordelingRepository extends JpaRepository<Klantbeoordeling, Long> {}
