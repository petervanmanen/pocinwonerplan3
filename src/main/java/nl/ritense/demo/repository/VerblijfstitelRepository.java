package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verblijfstitel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verblijfstitel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerblijfstitelRepository extends JpaRepository<Verblijfstitel, Long> {}
