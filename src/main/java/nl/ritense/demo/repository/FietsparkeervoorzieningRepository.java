package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Fietsparkeervoorziening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fietsparkeervoorziening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FietsparkeervoorzieningRepository extends JpaRepository<Fietsparkeervoorziening, Long> {}
