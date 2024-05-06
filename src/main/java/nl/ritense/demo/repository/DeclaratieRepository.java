package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Declaratie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Declaratie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeclaratieRepository extends JpaRepository<Declaratie, Long> {}
