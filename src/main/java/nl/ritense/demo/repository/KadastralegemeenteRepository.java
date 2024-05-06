package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kadastralegemeente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kadastralegemeente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KadastralegemeenteRepository extends JpaRepository<Kadastralegemeente, Long> {}
