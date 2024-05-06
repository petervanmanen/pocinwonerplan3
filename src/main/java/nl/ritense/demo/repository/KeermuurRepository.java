package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Keermuur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Keermuur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeermuurRepository extends JpaRepository<Keermuur, Long> {}
