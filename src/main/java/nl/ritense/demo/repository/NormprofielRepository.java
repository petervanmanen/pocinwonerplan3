package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Normprofiel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Normprofiel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormprofielRepository extends JpaRepository<Normprofiel, Long> {}
