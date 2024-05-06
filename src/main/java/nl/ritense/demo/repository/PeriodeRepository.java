package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Periode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Periode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodeRepository extends JpaRepository<Periode, Long> {}
