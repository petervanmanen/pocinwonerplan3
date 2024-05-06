package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Mast;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MastRepository extends JpaRepository<Mast, Long> {}
