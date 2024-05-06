package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Eroute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Eroute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErouteRepository extends JpaRepository<Eroute, Long> {}
