package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Begroeidterreindeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Begroeidterreindeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BegroeidterreindeelRepository extends JpaRepository<Begroeidterreindeel, Long> {}
