package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Asielstatushouder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Asielstatushouder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsielstatushouderRepository extends JpaRepository<Asielstatushouder, Long> {}
