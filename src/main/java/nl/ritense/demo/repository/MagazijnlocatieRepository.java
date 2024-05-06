package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Magazijnlocatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Magazijnlocatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MagazijnlocatieRepository extends JpaRepository<Magazijnlocatie, Long> {}
