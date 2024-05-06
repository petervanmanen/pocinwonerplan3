package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Land;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Land entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LandRepository extends JpaRepository<Land, Long> {}
