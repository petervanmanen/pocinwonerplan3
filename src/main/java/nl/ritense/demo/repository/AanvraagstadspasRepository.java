package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanvraagstadspas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraagstadspas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanvraagstadspasRepository extends JpaRepository<Aanvraagstadspas, Long> {}
