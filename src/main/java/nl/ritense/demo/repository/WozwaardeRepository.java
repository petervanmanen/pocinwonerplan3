package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wozwaarde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wozwaarde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WozwaardeRepository extends JpaRepository<Wozwaarde, Long> {}
