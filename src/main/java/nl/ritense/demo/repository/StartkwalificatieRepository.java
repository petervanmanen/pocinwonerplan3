package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Startkwalificatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Startkwalificatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StartkwalificatieRepository extends JpaRepository<Startkwalificatie, Long> {}
