package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vacature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vacature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VacatureRepository extends JpaRepository<Vacature, Long> {}
