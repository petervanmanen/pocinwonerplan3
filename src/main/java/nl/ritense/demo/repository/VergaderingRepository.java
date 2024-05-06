package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vergadering;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vergadering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VergaderingRepository extends JpaRepository<Vergadering, Long> {}
