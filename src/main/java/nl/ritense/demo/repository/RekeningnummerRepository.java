package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rekeningnummer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rekeningnummer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RekeningnummerRepository extends JpaRepository<Rekeningnummer, Long> {}
