package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Batchregel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Batchregel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BatchregelRepository extends JpaRepository<Batchregel, Long> {}
