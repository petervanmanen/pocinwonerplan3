package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Pachter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pachter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PachterRepository extends JpaRepository<Pachter, Long> {}
