package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Splitsingstekeningreferentie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Splitsingstekeningreferentie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SplitsingstekeningreferentieRepository extends JpaRepository<Splitsingstekeningreferentie, Long> {}
