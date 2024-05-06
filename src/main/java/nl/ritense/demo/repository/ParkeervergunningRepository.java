package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Parkeervergunning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parkeervergunning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkeervergunningRepository extends JpaRepository<Parkeervergunning, Long> {}
