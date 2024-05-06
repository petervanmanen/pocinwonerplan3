package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Licentie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Licentie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicentieRepository extends JpaRepository<Licentie, Long> {}
