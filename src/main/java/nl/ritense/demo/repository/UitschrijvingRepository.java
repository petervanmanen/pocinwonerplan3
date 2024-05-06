package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Uitschrijving;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Uitschrijving entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UitschrijvingRepository extends JpaRepository<Uitschrijving, Long> {}
