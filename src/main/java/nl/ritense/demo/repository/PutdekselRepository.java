package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Putdeksel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Putdeksel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PutdekselRepository extends JpaRepository<Putdeksel, Long> {}
