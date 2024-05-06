package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Belijning;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Belijning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BelijningRepository extends JpaRepository<Belijning, Long> {}
