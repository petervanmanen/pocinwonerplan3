package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Idealisatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Idealisatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdealisatieRepository extends JpaRepository<Idealisatie, Long> {}
