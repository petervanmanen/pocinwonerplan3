package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bouwdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bouwdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BouwdeelRepository extends JpaRepository<Bouwdeel, Long> {}
