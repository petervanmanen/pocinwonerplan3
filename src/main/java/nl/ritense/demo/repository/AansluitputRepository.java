package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aansluitput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aansluitput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AansluitputRepository extends JpaRepository<Aansluitput, Long> {}
