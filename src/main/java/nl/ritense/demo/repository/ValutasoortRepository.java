package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Valutasoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Valutasoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValutasoortRepository extends JpaRepository<Valutasoort, Long> {}
