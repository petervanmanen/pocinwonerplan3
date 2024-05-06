package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Informatiedakloosheid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Informatiedakloosheid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformatiedakloosheidRepository extends JpaRepository<Informatiedakloosheid, Long> {}
