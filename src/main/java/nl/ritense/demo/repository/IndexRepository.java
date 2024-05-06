package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Index;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Index entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndexRepository extends JpaRepository<Index, Long> {}
