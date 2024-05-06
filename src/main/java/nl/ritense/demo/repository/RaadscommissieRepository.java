package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Raadscommissie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Raadscommissie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaadscommissieRepository extends JpaRepository<Raadscommissie, Long> {}
