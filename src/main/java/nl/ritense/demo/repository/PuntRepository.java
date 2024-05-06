package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Punt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Punt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntRepository extends JpaRepository<Punt, Long> {}
