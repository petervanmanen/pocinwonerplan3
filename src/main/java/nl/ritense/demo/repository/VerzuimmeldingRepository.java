package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verzuimmelding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verzuimmelding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerzuimmeldingRepository extends JpaRepository<Verzuimmelding, Long> {}
