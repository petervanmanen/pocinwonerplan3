package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aantal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aantal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AantalRepository extends JpaRepository<Aantal, Long> {}
