package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Parkeergarage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parkeergarage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkeergarageRepository extends JpaRepository<Parkeergarage, Long> {}
