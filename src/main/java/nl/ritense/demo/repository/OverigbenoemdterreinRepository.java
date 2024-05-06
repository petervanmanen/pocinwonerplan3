package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Overigbenoemdterrein;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Overigbenoemdterrein entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OverigbenoemdterreinRepository extends JpaRepository<Overigbenoemdterrein, Long> {}
