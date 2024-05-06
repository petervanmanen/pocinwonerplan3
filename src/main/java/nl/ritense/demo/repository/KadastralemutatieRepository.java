package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kadastralemutatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kadastralemutatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KadastralemutatieRepository extends JpaRepository<Kadastralemutatie, Long> {}
