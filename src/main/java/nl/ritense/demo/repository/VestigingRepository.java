package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Vestiging;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vestiging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VestigingRepository extends JpaRepository<Vestiging, Long> {}
