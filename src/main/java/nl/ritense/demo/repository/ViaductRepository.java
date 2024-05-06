package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Viaduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Viaduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViaductRepository extends JpaRepository<Viaduct, Long> {}
