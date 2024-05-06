package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leerroute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leerroute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeerrouteRepository extends JpaRepository<Leerroute, Long> {}
