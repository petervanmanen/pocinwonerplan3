package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ligplaats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ligplaats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigplaatsRepository extends JpaRepository<Ligplaats, Long> {}
