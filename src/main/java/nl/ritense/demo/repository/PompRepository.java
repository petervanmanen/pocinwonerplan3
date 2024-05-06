package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Pomp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pomp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PompRepository extends JpaRepository<Pomp, Long> {}
