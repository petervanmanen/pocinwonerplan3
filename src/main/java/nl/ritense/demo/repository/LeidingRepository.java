package nl.ritense.demo.repository;

import nl.ritense.demo.domain.Leiding;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Leiding entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeidingRepository extends JpaRepository<Leiding, Long> {}
