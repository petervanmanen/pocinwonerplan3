package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Nationaliteit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nationaliteit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NationaliteitRepository extends JpaRepository<Nationaliteit, Long> {}
