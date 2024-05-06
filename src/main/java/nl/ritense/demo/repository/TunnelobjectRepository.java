package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Tunnelobject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tunnelobject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TunnelobjectRepository extends JpaRepository<Tunnelobject, Long> {}
