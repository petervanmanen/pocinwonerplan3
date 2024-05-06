package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kenmerkenzaak;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kenmerkenzaak entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KenmerkenzaakRepository extends JpaRepository<Kenmerkenzaak, Long> {}
