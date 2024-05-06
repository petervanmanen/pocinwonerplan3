package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Sportlocatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sportlocatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SportlocatieRepository extends JpaRepository<Sportlocatie, Long> {}
