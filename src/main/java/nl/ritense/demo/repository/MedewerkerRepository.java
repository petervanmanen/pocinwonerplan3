package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Medewerker;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Medewerker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedewerkerRepository extends JpaRepository<Medewerker, Long> {}
