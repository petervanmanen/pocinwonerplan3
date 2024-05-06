package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Moormelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Moormelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MoormeldingRepository extends JpaRepository<Moormelding, Long> {}
