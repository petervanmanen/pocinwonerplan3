package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Plank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Plank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlankRepository extends JpaRepository<Plank, Long> {}
