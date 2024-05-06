package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aanvraagvrijstelling;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aanvraagvrijstelling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AanvraagvrijstellingRepository extends JpaRepository<Aanvraagvrijstelling, Long> {}
