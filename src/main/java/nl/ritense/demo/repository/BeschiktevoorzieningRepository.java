package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beschiktevoorziening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beschiktevoorziening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeschiktevoorzieningRepository extends JpaRepository<Beschiktevoorziening, Long> {}
