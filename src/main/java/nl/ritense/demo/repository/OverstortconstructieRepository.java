package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overstortconstructie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overstortconstructie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverstortconstructieRepository extends JpaRepository<Overstortconstructie, Long> {}
