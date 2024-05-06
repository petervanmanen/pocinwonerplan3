package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Projectlocatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Projectlocatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectlocatieRepository extends JpaRepository<Projectlocatie, Long> {}
