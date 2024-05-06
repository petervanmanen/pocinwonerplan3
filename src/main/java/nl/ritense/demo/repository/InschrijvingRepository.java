package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inschrijving;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inschrijving entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InschrijvingRepository extends JpaRepository<Inschrijving, Long> {}
