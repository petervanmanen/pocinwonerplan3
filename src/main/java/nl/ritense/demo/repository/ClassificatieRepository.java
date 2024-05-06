package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Classificatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Classificatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassificatieRepository extends JpaRepository<Classificatie, String> {}
