package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Ambacht;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ambacht entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmbachtRepository extends JpaRepository<Ambacht, Long> {}
