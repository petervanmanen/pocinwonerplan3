package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Solitaireplant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Solitaireplant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolitaireplantRepository extends JpaRepository<Solitaireplant, Long> {}
