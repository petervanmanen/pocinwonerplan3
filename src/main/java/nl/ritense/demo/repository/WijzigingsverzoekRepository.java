package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wijzigingsverzoek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wijzigingsverzoek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WijzigingsverzoekRepository extends JpaRepository<Wijzigingsverzoek, Long> {}
