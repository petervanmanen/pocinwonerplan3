package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Straatsectie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Straatsectie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StraatsectieRepository extends JpaRepository<Straatsectie, Long> {}
