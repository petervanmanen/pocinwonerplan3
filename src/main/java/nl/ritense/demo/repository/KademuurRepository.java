package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Kademuur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Kademuur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KademuurRepository extends JpaRepository<Kademuur, Long> {}
