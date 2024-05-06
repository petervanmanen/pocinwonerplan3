package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Functie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Functie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctieRepository extends JpaRepository<Functie, Long> {}
