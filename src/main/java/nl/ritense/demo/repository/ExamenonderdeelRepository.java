package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Examenonderdeel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Examenonderdeel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamenonderdeelRepository extends JpaRepository<Examenonderdeel, Long> {}
