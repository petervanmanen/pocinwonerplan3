package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Reservering;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Reservering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReserveringRepository extends JpaRepository<Reservering, Long> {}
