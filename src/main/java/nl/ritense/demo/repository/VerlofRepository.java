package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Verlof;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Verlof entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerlofRepository extends JpaRepository<Verlof, Long> {}
