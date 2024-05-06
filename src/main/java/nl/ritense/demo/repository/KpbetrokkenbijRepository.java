package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kpbetrokkenbij;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kpbetrokkenbij entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KpbetrokkenbijRepository extends JpaRepository<Kpbetrokkenbij, Long> {}
