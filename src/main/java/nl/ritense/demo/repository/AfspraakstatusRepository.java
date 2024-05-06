package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Afspraakstatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Afspraakstatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AfspraakstatusRepository extends JpaRepository<Afspraakstatus, Long> {}
