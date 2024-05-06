package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Factuur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Factuur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactuurRepository extends JpaRepository<Factuur, Long> {}
