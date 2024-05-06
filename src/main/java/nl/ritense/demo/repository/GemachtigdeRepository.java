package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Gemachtigde;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Gemachtigde entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GemachtigdeRepository extends JpaRepository<Gemachtigde, Long> {}
