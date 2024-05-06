package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Collegelid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Collegelid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollegelidRepository extends JpaRepository<Collegelid, Long> {}
