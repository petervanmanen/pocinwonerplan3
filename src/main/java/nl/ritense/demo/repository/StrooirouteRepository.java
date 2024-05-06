package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Strooiroute;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Strooiroute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StrooirouteRepository extends JpaRepository<Strooiroute, Long> {}
