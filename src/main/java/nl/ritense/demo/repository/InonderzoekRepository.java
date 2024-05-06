package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inonderzoek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inonderzoek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InonderzoekRepository extends JpaRepository<Inonderzoek, Long> {}
