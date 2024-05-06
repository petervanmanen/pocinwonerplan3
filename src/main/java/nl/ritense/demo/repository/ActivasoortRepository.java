package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Activasoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Activasoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivasoortRepository extends JpaRepository<Activasoort, Long> {}
