package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Parkeerrecht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parkeerrecht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkeerrechtRepository extends JpaRepository<Parkeerrecht, Long> {}
