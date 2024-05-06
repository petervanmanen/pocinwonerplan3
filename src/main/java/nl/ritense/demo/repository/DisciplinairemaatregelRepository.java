package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Disciplinairemaatregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Disciplinairemaatregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplinairemaatregelRepository extends JpaRepository<Disciplinairemaatregel, Long> {}
