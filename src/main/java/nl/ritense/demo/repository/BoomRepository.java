package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Boom;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Boom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoomRepository extends JpaRepository<Boom, Long> {}
