package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Benoemdterrein;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Benoemdterrein entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenoemdterreinRepository extends JpaRepository<Benoemdterrein, Long> {}
