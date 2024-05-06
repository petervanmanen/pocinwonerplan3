package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Rioleringsgebied;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rioleringsgebied entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RioleringsgebiedRepository extends JpaRepository<Rioleringsgebied, Long> {}
