package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Aomstatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Aomstatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AomstatusRepository extends JpaRepository<Aomstatus, Long> {}
