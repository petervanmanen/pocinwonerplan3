package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Voertuig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Voertuig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoertuigRepository extends JpaRepository<Voertuig, Long> {}
