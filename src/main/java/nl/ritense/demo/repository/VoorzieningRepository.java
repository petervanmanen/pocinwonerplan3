package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Voorziening;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voorziening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoorzieningRepository extends JpaRepository<Voorziening, Long> {}
