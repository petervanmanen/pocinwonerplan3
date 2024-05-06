package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Inburgeringstraject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inburgeringstraject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InburgeringstrajectRepository extends JpaRepository<Inburgeringstraject, Long> {}
