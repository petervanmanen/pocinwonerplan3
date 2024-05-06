package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Startformulieraanbesteden;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Startformulieraanbesteden entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StartformulieraanbestedenRepository extends JpaRepository<Startformulieraanbesteden, Long> {}
