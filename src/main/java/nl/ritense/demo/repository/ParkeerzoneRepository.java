package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Parkeerzone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parkeerzone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkeerzoneRepository extends JpaRepository<Parkeerzone, Long> {}
