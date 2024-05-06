package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Beschermdestatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Beschermdestatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeschermdestatusRepository extends JpaRepository<Beschermdestatus, Long> {}
