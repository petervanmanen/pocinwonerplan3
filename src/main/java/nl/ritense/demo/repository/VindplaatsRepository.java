package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vindplaats;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vindplaats entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VindplaatsRepository extends JpaRepository<Vindplaats, Long> {}
