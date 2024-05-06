package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Mutatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mutatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MutatieRepository extends JpaRepository<Mutatie, Long> {}
