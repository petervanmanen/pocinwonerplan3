package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Waterdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Waterdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaterdeelRepository extends JpaRepository<Waterdeel, Long> {}
