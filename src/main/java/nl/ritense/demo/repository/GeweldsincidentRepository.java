package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Geweldsincident;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Geweldsincident entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeweldsincidentRepository extends JpaRepository<Geweldsincident, Long> {}
