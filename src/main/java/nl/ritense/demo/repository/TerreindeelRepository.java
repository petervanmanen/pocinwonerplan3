package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Terreindeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Terreindeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerreindeelRepository extends JpaRepository<Terreindeel, Long> {}
