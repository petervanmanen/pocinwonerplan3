package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Handelsnamenvestiging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Handelsnamenvestiging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandelsnamenvestigingRepository extends JpaRepository<Handelsnamenvestiging, Long> {}
