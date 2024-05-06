package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Bevinding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bevinding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BevindingRepository extends JpaRepository<Bevinding, Long> {}
