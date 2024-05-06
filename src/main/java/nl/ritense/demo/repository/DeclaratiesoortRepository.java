package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Declaratiesoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Declaratiesoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeclaratiesoortRepository extends JpaRepository<Declaratiesoort, Long> {}
