package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Trajectsoort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Trajectsoort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajectsoortRepository extends JpaRepository<Trajectsoort, Long> {}
