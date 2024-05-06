package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfbuitenland;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfbuitenland entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfbuitenlandRepository extends JpaRepository<Verblijfbuitenland, Long> {}
