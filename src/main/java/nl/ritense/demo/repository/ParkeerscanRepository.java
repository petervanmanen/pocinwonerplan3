package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Parkeerscan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parkeerscan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkeerscanRepository extends JpaRepository<Parkeerscan, Long> {}
