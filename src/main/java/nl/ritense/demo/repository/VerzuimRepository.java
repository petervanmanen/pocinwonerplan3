package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verzuim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verzuim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerzuimRepository extends JpaRepository<Verzuim, Long> {}
