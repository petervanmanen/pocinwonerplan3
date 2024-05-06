package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Soortdisciplinairemaatregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soortdisciplinairemaatregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoortdisciplinairemaatregelRepository extends JpaRepository<Soortdisciplinairemaatregel, Long> {}
