package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kwalificatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kwalificatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KwalificatieRepository extends JpaRepository<Kwalificatie, Long> {}
