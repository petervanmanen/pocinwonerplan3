package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inkomensvoorziening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inkomensvoorziening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InkomensvoorzieningRepository extends JpaRepository<Inkomensvoorziening, Long> {}
