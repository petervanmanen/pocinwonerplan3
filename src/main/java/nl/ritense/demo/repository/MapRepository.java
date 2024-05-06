package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Map;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Map entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MapRepository extends JpaRepository<Map, Long> {}
