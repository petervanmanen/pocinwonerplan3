package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfplaats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfplaats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfplaatsRepository extends JpaRepository<Verblijfplaats, Long> {}
