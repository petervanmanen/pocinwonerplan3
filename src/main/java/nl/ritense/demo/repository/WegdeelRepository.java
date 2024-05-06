package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Wegdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wegdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WegdeelRepository extends JpaRepository<Wegdeel, Long> {}
