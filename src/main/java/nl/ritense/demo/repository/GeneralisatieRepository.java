package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Generalisatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Generalisatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneralisatieRepository extends JpaRepository<Generalisatie, String> {}
